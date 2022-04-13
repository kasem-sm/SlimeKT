/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slime.auth_api.AuthState
import com.slime.auth_api.ObserveAuthState
import com.slime.ui_home.HomeState.Companion.DEFAULT_SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.article.domain.interactors.GetArticles
import kasem.sm.article.domain.observers.ObserveArticles
import kasem.sm.article.domain.observers.ObserveDailyReadArticle
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.topic.domain.interactors.GetSubscribedTopics
import kasem.sm.topic.domain.observers.ObserveSubscribedTopics
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber

@HiltViewModel
class HomeVM @Inject constructor(
    private val getArticles: GetArticles,
    private val getSubscribedTopics: GetSubscribedTopics,
    private val savedStateHandle: SavedStateHandle,
    private val dispatchers: SlimeDispatchers,
    private val observeArticles: ObserveArticles,
    private val observeAuthState: ObserveAuthState,
    observeDailyReadArticle: ObserveDailyReadArticle,
    observeSubscribedTopics: ObserveSubscribedTopics,
) : ViewModel() {

    private val searchQuery = SavedMutableState(
        savedStateHandle,
        QUERY_KEY,
        defValue = DEFAULT_SEARCH_QUERY
    )

    private val loadingStatus = ObservableLoader()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val state = combineFlows(
        searchQuery.flow,
        loadingStatus.flow,
        observeSubscribedTopics.flow,
        observeDailyReadArticle.flow,
        observeArticles.flow,
    ) { currentQuery, isLoading, topics,
        dailyReadArticle, articles ->
        HomeState(
            currentQuery = currentQuery,
            isLoading = isLoading,
            topics = topics,
            dailyReadArticle = dailyReadArticle,
            articles = articles,
        )
    }.stateIn(viewModelScope, HomeState.EMPTY)

    init {
        refresh()

        observeAuthState()

        observeArticles()

        observeSubscribedTopics.join(
            coroutineScope = viewModelScope + dispatchers.main,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeDailyReadArticle.join(
            coroutineScope = viewModelScope + dispatchers.main,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    private fun observeAuthState() {
        viewModelScope.launch {
            observeAuthState
                .joinAndCollect(viewModelScope + dispatchers.main)
                .filter { it == AuthState.LOGGED_IN }
                .collect {
                    Timber.d("Auth State is logged in, refreshing")
                    refresh(subscriptionOnly = true)
                }
        }
    }

    private fun observeArticles() {
        observeArticles.join(
            params = searchQuery.value,
            coroutineScope = viewModelScope + dispatchers.main,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    fun refresh(subscriptionOnly: Boolean = false) {
        if (subscriptionOnly) {
            getSubscribedTopics()
            return
        }
        getArticles()
        getSubscribedTopics()
    }

    private fun getArticles() {
        viewModelScope.launch(dispatchers.main) {
            getArticles.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
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
        searchQuery.value = newValue
        observeArticles()
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
        if (searchQuery.value.isNotEmpty()) {
            onQueryChange(DEFAULT_SEARCH_QUERY)
        }
    }

    fun queryIsNotEmpty(): Boolean {
        return state.value.currentQuery.isNotEmpty()
    }

    companion object {
        const val LIST_POSITION_KEY = "slime_list_position"
        const val QUERY_KEY = "slime_query"
    }
}
