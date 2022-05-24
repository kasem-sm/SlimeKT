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
import kasem.sm.article.domain.interactors.BookmarkArticle
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel
class HomeVM @Inject constructor(
    private val getArticles: GetArticles,
    private val bookmarkArticle: BookmarkArticle,
    private val getSubscribedTopics: GetSubscribedTopics,
    private val dispatchers: SlimeDispatchers,
    private val observeArticles: ObserveArticles,
    private val observeAuthState: ObserveAuthState,
    observeDailyReadArticle: ObserveDailyReadArticle,
    observeSubscribedTopics: ObserveSubscribedTopics,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val searchQuery = SavedMutableState(
        savedStateHandle,
        QUERY_KEY,
        defValue = DEFAULT_SEARCH_QUERY
    )

    private val loadingStatus = ObservableLoader()

    private val isUserAuthenticated = SavedMutableState(
        savedStateHandle,
        USER_AUTH_KEY,
        defValue = false
    )

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val state = combineFlows(
        searchQuery.flow,
        loadingStatus.flow,
        isUserAuthenticated.flow,
        observeSubscribedTopics.flow,
        observeDailyReadArticle.flow,
        observeArticles.flow,
    ) { currentQuery, isLoading, isUserAuthenticated, topics,
        dailyReadArticle, articles ->
        HomeState(
            currentQuery = currentQuery,
            isLoading = isLoading,
            topics = topics,
            dailyReadArticle = dailyReadArticle,
            articles = articles,
            isUserAuthenticated = isUserAuthenticated
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
        viewModelScope.launch(dispatchers.main) {
            observeAuthState.joinAndCollect(
                coroutineScope = viewModelScope + dispatchers.main
            ).collectLatest {
                onQueryChange(DEFAULT_SEARCH_QUERY)
                refresh(subscriptionOnly = true)
                isUserAuthenticated.value = it == AuthState.LOGGED_IN
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

    fun updateBookmarkStatus(articleId: Int) {
        viewModelScope.launch {
            bookmarkArticle.execute(articleId).collect()
        }
    }

    companion object {
        const val QUERY_KEY = "slime_query"
        const val USER_AUTH_KEY = "slime_home_is_user_authenticated"
    }
}
