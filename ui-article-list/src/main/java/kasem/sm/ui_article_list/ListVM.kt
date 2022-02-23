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
import kasem.sm.article.domain.interactors.ArticlePager
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.ObservableLoader.Companion.Loader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.core.interfaces.Session
import kasem.sm.core.interfaces.Tasks
import kasem.sm.topic.domain.interactors.GetTopicById
import kasem.sm.topic.domain.interactors.ObserveTopicById
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.navigate
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ListVM @Inject constructor(
    private val pager: ArticlePager,
    private val getTopic: GetTopicById,
    private val savedStateHandle: SavedStateHandle,
    private val slimeDispatchers: SlimeDispatchers,
    private val session: Session,
    private val tasks: Tasks,
    private val observeTopic: ObserveTopicById,
) : ViewModel() {

    private val topicId = savedStateHandle.get<String>(TOPIC_ID_KEY)!!

    private val topicQuery = savedStateHandle.get<String>(TOPIC_QUERY_KEY)!!

    private val scrollPosition = SavedMutableState(
        savedStateHandle,
        LIST_POSITION_KEY,
        defValue = 0
    )

    private val currentPage = SavedMutableState(
        savedStateHandle,
        PAGE_KEY,
        defValue = 0
    )

    private val isUserAuthenticated = SavedMutableState(
        savedStateHandle,
        "user_authenticated",
        defValue = false
    )

    private val isSubscriptionInProgress = ObservableLoader()

    private val topicLoadingStatus = ObservableLoader()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val state: StateFlow<ListState> = combineFlows(
        currentPage.flow,
        pager.loadingStatus.flow,
        topicLoadingStatus.flow,
        pager.endOfPagination,
        pager.articles,
        observeTopic.flow,
        isSubscriptionInProgress.flow,
        isUserAuthenticated.flow
    ) { currentPage, paginationLoadStatus, topicLoadStatus, endOfPagination,
        articles, topic, isSubscriptionInProgress, isUserAuthenticated ->
        ListState(
            currentPage = currentPage,
            isLoading = paginationLoadStatus || topicLoadStatus,
            endOfPagination = endOfPagination,
            articles = articles,
            topic = topic,
            isSubscriptionInProgress = isSubscriptionInProgress,
            isUserAuthenticated = isUserAuthenticated
        )
    }.stateIn(viewModelScope, ListState.EMPTY)

    init {
        observeAuthenticationState()

        initializePager()

        observeTopic()

        getTopic()
    }

    private fun observeAuthenticationState() {
        viewModelScope.launch(slimeDispatchers.main) {
            session.observeAuthenticationState().collectLatest {
                isUserAuthenticated.value = it
            }
        }
    }

    private fun initializePager(
        topicQuery: String = this.topicQuery,
    ) {
        viewModelScope.launch(slimeDispatchers.main) {
            pager.initialize(
                topic = topicQuery,
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
            if (scrollPosition.value == 0) {
                viewModelScope.launch(slimeDispatchers.main) {
                    pager.refresh()
                }
            }
        }
    }

    fun executeNextPage(updatedPage: Int? = null) {
        viewModelScope.launch(slimeDispatchers.main) {
            pager.executeNextPage(updatedPage)
        }
    }

    fun checkAuthenticationStatus() {
        viewModelScope.launch {
            if (!isUserAuthenticated.value) {
                _uiEvent.emit(navigate(Routes.RegisterScreen.route))
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.main) {
            pager.refresh()
        }

        // Refresh
        getTopic()
    }

    private fun observeTopic() {
        observeTopic.join(
            params = topicId,
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    private fun getTopic() {
        viewModelScope.launch(slimeDispatchers.main) {
            getTopic.execute(topicId).collect(
                loader = topicLoadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun updateSubscription(
        onSuccess: () -> Unit
    ) {
        isSubscriptionInProgress.invoke(Loader.START)
        viewModelScope.launch(slimeDispatchers.main) {
            tasks.updateSubscriptionStatus(
                ids = listOf(topicId)
            ).collect(
                loader = isSubscriptionInProgress,
                onError = { _uiEvent.emit(showMessage(it)) },
                onSuccess = {
                    _uiEvent.emit(showMessage(string.common_success_msg))
                    onSuccess()
                    isSubscriptionInProgress.invoke(Loader.STOP)
                },
            )
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
        const val TOPIC_QUERY_KEY = "slime_topic"
        const val TOPIC_ID_KEY = "slime_topic_id"
    }
}