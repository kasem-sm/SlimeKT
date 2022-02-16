/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slime.ui_home.HomeState.Companion.DEFAULT_CATEGORY_QUERY
import com.slime.ui_home.HomeState.Companion.DEFAULT_SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.feature_article.domain.interactors.ArticlePager
import kasem.sm.feature_article.domain.interactors.ObserveDailyReadArticle
import kasem.sm.feature_category.domain.interactors.GetSubscribedCategories
import kasem.sm.feature_category.domain.interactors.ObserveSubscribedCategories
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
class HomeViewModel @Inject constructor(
    private val getSubscribedCategories: GetSubscribedCategories,
    private val pager: ArticlePager,
    private val savedStateHandle: SavedStateHandle,
    private val slimeDispatchers: SlimeDispatchers,
    observeDailyReadArticle: ObserveDailyReadArticle,
    observeSubscribedCategories: ObserveSubscribedCategories,
) : ViewModel() {

    private val categoryQuery = SavedMutableState(
        savedStateHandle,
        CATEGORY_QUERY_KEY,
        defValue = DEFAULT_CATEGORY_QUERY
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
        categoryQuery.flow,
        searchQuery.flow,
        currentPage.flow,
        loadingStatus.flow,
        observeSubscribedCategories.flow,
        observeDailyReadArticle.flow,
        pager.loadingStatus.flow,
        pager.endOfPagination,
        pager.articles,
    ) { currentCategory, currentQuery, currentPage, isLoading, categories, dailyReadArticle,
        pagingLoadingState, endOfPagination, articles ->
        HomeState(
            currentCategory = currentCategory,
            currentQuery = currentQuery,
            currentPage = currentPage,
            isLoading = isLoading || pagingLoadingState,
            categories = categories,
            dailyReadArticle = dailyReadArticle,
            endOfPagination = endOfPagination,
            articles = articles,
        )
    }.stateIn(viewModelScope, HomeState.EMPTY)

    init {
        initializePager()

        observeSearchQuery()

        observeSubscribedCategories.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeDailyReadArticle.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    private fun initializePager(
        categoryQuery: String = this.categoryQuery.value,
        searchQuery: String = this.searchQuery.value,
        forceRefresh: Boolean = false
    ) {
        job?.cancel()
        job = viewModelScope.launch(slimeDispatchers.main) {
            pager.initialize(
                category = categoryQuery,
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
                refresh()
            }
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch(slimeDispatchers.main) {
            searchQuery.flow
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    job = launch {
                        if (scrollPosition.value == 0) {
                            // Reinitialize and refresh pager with updated search query
                            initializePager(
                                searchQuery = query,
                                forceRefresh = true
                            )
                        }
                    }
                    job!!.join()
                }
        }
    }

    fun refresh() {
        job?.cancel()
        job = viewModelScope.launch(slimeDispatchers.main) {
            pager.refresh()
        }

        viewModelScope.launch(slimeDispatchers.main) {
            getSubscribedCategories.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun onQueryChange(newValue: String) {
        job?.cancel()
        searchQuery.value = newValue
    }

    fun onCategoryChange(newValue: String) {
        job?.cancel()
        categoryQuery.value = newValue
        // Reinitialize and refresh pager with updated category query
        initializePager(
            categoryQuery = newValue,
            forceRefresh = true
        )
    }

    fun executeNextPage(updatedPage: Int? = null) {
        job = viewModelScope.launch(slimeDispatchers.main) {
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

    companion object {
        const val LIST_POSITION_KEY = "slime_list_position"
        const val PAGE_KEY = "slime_page"
        const val QUERY_KEY = "slime_query"
        const val CATEGORY_QUERY_KEY = "slime_category"
    }
}
