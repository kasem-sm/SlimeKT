/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.article.domain.interactors.BookmarkArticle
import kasem.sm.article.domain.observers.ObserveArticlesByTopic
import kasem.sm.auth_api.AuthState
import kasem.sm.auth_api.ObserveAuthState
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.util.Destination
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.task_api.Tasks
import kasem.sm.topic.domain.interactors.GetTopicById
import kasem.sm.topic.domain.observers.ObserveTopicById
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.navigate
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ListVM @Inject constructor(
    private val getTopic: GetTopicById,
    private val bookmarkArticle: BookmarkArticle,
    private val dispatchers: SlimeDispatchers,
    private val tasks: Tasks,
    private val observeAuthState: ObserveAuthState,
    private val observeTopic: ObserveTopicById,
    private val observeArticles: ObserveArticlesByTopic,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val topicId = savedStateHandle.get<String>(TOPIC_ID_KEY)!!

    private val topicQuery = savedStateHandle.get<String>(TOPIC_QUERY_KEY)!!

    private val isUserAuthenticated = SavedMutableState(
        savedStateHandle,
        USER_AUTHENTICATION_KEY,
        defValue = false
    )

    private val subscriptionProgress = ObservableLoader()

    private val loadingStatus = ObservableLoader()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val state: StateFlow<ListState> = combineFlows(
        loadingStatus.flow,
        observeTopic.flow,
        subscriptionProgress.flow,
        observeAuthState.flow,
        observeArticles.flow
    ) { topicLoadStatus, topic, isSubscriptionInProgress,
        state, articles ->
        ListState(
            isLoading = topicLoadStatus,
            articles = articles,
            topic = topic,
            isSubscriptionInProgress = isSubscriptionInProgress,
            isUserAuthenticated = state == AuthState.LOGGED_IN
        )
    }.stateIn(viewModelScope, ListState.EMPTY)

    init {
        observeData()

        refresh()
    }

    fun checkAuthenticationStatus() {
        viewModelScope.launch {
            if (!isUserAuthenticated.value) {
                _uiEvent.emit(navigate(Destination.LoginScreen.route))
            }
        }
    }

    fun updateBookmarkStatus(articleId: Int) {
        viewModelScope.launch {
            bookmarkArticle.execute(articleId).collect()
        }
    }

    fun refresh() {
        getTopic()
    }

    private fun observeData() {
        viewModelScope.launch {
            observeAuthState.joinAndCollect(viewModelScope).collectLatest {
                isUserAuthenticated.value = it == AuthState.LOGGED_IN
                // Refresh subscription status when auth State changes
                refresh()
            }
        }

        observeTopic.join(
            params = topicId,
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeArticles.join(
            params = topicQuery,
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    private fun getTopic() {
        viewModelScope.launch(dispatchers.main) {
            getTopic.execute(topicId).collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun updateSubscription() {
        subscriptionProgress.start()
        viewModelScope.launch(dispatchers.main) {
            tasks.updateSubscriptionStatus(
                ids = listOf(topicId)
            ).collect { result ->
                when {
                    result.isSuccess -> _uiEvent.emit(showMessage(string.common_success_msg))
                    result.isFailure -> _uiEvent.emit(showMessage(string.common_error_msg))
                }
                subscriptionProgress.stop()
            }
        }
    }

    companion object {
        const val LIST_POSITION_KEY = "slime_list_position"
        const val TOPIC_QUERY_KEY = "slime_topic"
        const val TOPIC_ID_KEY = "slime_topic_id"
        const val USER_AUTHENTICATION_KEY = "user_authenticated"
    }
}
