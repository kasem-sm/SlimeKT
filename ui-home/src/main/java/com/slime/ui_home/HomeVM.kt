/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slime.ui_home.HomeState.Companion.DEFAULT_SEARCH_QUERY
import com.slime.ui_home.HomeState.Companion.DEFAULT_TOPIC_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.article.domain.interactors.ArticlePager
import kasem.sm.article.domain.interactors.ObserveDailyReadArticle
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.core.session.ObserveAuthState
import kasem.sm.topic.domain.interactors.GetSubscribedTopics
import kasem.sm.topic.domain.interactors.ObserveSubscribedTopics
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class HomeVM @Inject constructor(
    private val getSubscribedTopics: GetSubscribedTopics,
    private val pager: ArticlePager,
    private val savedStateHandle: SavedStateHandle,
    private val dispatchers: SlimeDispatchers,
    private val observeDailyReadArticle: ObserveDailyReadArticle,
    private val observeSubscribedTopics: ObserveSubscribedTopics,
    private val observeAuthState: ObserveAuthState,
) : ViewModel() {

    private val topicQuery = SavedMutableState(
        savedStateHandle,
        TOPIC_QUERY_KEY,
        defValue = DEFAULT_TOPIC_QUERY
    )

    private val searchQuery = SavedMutableState(
        savedStateHandle,
        QUERY_KEY,
        defValue = DEFAULT_SEARCH_QUERY
    )

    private val scrollPosition = SavedMutableState(savedStateHandle, LIST_POSITION_KEY, 0)
    private val currentPage = SavedMutableState(savedStateHandle, PAGE_KEY, 0)
    private val loadingStatus = ObservableLoader()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var job: Job? = null

    val state = combineFlows(
        topicQuery.flow,
        searchQuery.flow,
        currentPage.flow,
        loadingStatus.flow,
        observeSubscribedTopics.flow,
        observeDailyReadArticle.flow,
        pager.loadingStatus.flow,
        pager.endOfPagination,
        pager.articles,
    ) { currentTopic, currentQuery, currentPage, isLoading, topics, dailyReadArticle,
        pagingLoadingState, endOfPagination, articles ->
        HomeState(
            currentTopic = currentTopic,
            currentQuery = currentQuery,
            currentPage = currentPage,
            paginationLoadStatus = pagingLoadingState,
            isLoading = isLoading,
            topics = topics,
            dailyReadArticle = dailyReadArticle,
            endOfPagination = endOfPagination,
            articles = articles,
        )
    }.stateIn(viewModelScope, HomeState.EMPTY)

    init {
        initializePager()

        observeData()

        getSubscribedTopics()
    }

    private fun initializePager(
        topicQuery: String = this.topicQuery.value,
        searchQuery: String = this.searchQuery.value,
        forceRefresh: Boolean = false
    ) {
        job?.cancel()
        job = viewModelScope.launch(dispatchers.main) {
            pager.initialize(
                topic = topicQuery,
                query = searchQuery,
                page = currentPage.value,
                saveLoadedPage = { currentPage.value = it },
                onError = { _uiEvent.emit(showMessage(it)) },
                scrollPosition = scrollPosition.value,
                onRestorationComplete = {
                    _uiEvent.emit(UiEvent.SendData(scrollPosition.value))
                    scrollPosition.value = 0
                    executeNextPage(currentPage.value)
                }
            )
            if (forceRefresh) {
                job?.cancel()
                job = viewModelScope.launch(dispatchers.main) {
                    pager.refresh()
                }
            }
        }
    }

    private fun observeData() {
        viewModelScope.launch(dispatchers.main) {
            observeAuthState.flow.collect {
                refresh()
            }
        }

        viewModelScope.launch(dispatchers.main) {
            searchQuery.flow
                .debounce(800)
                .distinctUntilChanged()
                .collectLatest { query ->
                    job?.cancel()
                    job = launch {
                        if (scrollPosition.value == 0) {
                            // Reinitialize and refresh pager with updated search query
                            onQueryChange(query)
                        }
                    }
                    job?.join()
                }
        }

        observeAuthState.join(viewModelScope)

        observeSubscribedTopics.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeDailyReadArticle.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    fun refresh() {
        job?.cancel()
        job = viewModelScope.launch(dispatchers.main) {
            pager.refresh()
        }

        // Refresh
        getSubscribedTopics()
    }

    private fun getSubscribedTopics() {
        viewModelScope.launch(dispatchers.main) {
            getSubscribedTopics.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun onQueryChange(newValue: String) {
        job?.cancel()
        searchQuery.value = newValue
        // Reinitialize and refresh pager with updated search query
        initializePager(
            searchQuery = newValue,
            forceRefresh = true
        )
    }

    fun onTopicChange(newValue: String) {
        job?.cancel()
        topicQuery.value = newValue
        // Reinitialize and refresh pager with updated topic query
        initializePager(
            topicQuery = newValue,
            forceRefresh = true
        )
    }

    fun executeNextPage(updatedPage: Int? = null) {
        job?.cancel()
        job = viewModelScope.launch(dispatchers.main) {
            pager.executeNextPage(updatedPage)
        }
    }

    /**
     * Saves the latest scroll position to the savedState
     * as after process death, we will retrieve it from savedState
     * to scroll the list to the position for best user experience.
     */
    fun saveScrollPosition(updatedPosition: Int) {
        savedStateHandle[LIST_POSITION_KEY] = updatedPosition
    }

    fun resetToDefaults() {
        when {
            searchQuery.value.isNotEmpty() -> onQueryChange(DEFAULT_SEARCH_QUERY)
            topicQuery.value.isNotEmpty() -> onTopicChange(DEFAULT_TOPIC_QUERY)
        }
    }

    companion object {
        const val LIST_POSITION_KEY = "slime_list_position"
        const val PAGE_KEY = "slime_page"
        const val QUERY_KEY = "slime_query"
        const val TOPIC_QUERY_KEY = "slime_topic"
    }
}
