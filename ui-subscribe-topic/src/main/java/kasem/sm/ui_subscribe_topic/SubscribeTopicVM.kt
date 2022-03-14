/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.core.session.AuthState
import kasem.sm.core.session.ObserveAuthState
import kasem.sm.topic.domain.interactors.GetInExploreTopics
import kasem.sm.topic.domain.interactors.ObserveInExploreTopics
import kasem.sm.topic.domain.model.Topic
import kasem.sm.topic.worker.SubscribeTopicManager
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.navigate
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kasem.sm.ui_core.success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class SubscribeTopicVM @Inject constructor(
    /** Topics that are not subscribed can be requested through [getInExploreTopics] **/
    private val getInExploreTopics: GetInExploreTopics,
    private val subscribeTopicManager: SubscribeTopicManager,
    private val dispatchers: SlimeDispatchers,
    private val observeAuthState: ObserveAuthState,
    private val observeInExploreTopics: ObserveInExploreTopics,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    private val isSubscriptionInProgress = ObservableLoader()

    private val listOfTopics = MutableStateFlow(emptyList<Topic>())

    private val isUserAuthenticated = SavedMutableState(
        savedStateHandle,
        USER_AUTH_KEY,
        defValue = false
    )

    val state: StateFlow<SubscribeTopicState> = combine(
        loadingStatus.flow,
        isSubscriptionInProgress.flow,
        listOfTopics,
        isUserAuthenticated.flow,
    ) { loadStatus, isSubscriptionInProgress, topics, isUserAuthenticated ->
        SubscribeTopicState(
            isLoading = loadStatus,
            topics = topics,
            isUserAuthenticated = isUserAuthenticated,
            isSubscriptionInProgress = isSubscriptionInProgress
        )
    }.stateIn(viewModelScope, SubscribeTopicState.EMPTY)

    init {
        viewModelScope.launch(dispatchers.main) {
            observeAuthState.flow.collect {
                refresh()
                isUserAuthenticated.value = it == AuthState.LOGGED_IN
            }
        }

        observeData()
    }

    private fun observeData() {
        observeAuthState.join(viewModelScope)

        viewModelScope.launch(dispatchers.main) {
            observeInExploreTopics.joinAndCollect(
                coroutineScope = viewModelScope,
                onError = { _uiEvent.emit(showMessage(it)) },
            ).collectLatest {
                listOfTopics.value = it
            }
        }
    }

    fun checkAuthenticationStatus() {
        viewModelScope.launch(dispatchers.main) {
            if (!isUserAuthenticated.value) {
                _uiEvent.emit(navigate(Routes.LoginScreen.route))
            }
        }
    }

    fun updateList(itemsIndex: Int) {
        viewModelScope.launch(dispatchers.main) {
            val maxSelectableTopicCount = listOfTopics.value.count { it.isSelected } == 5

            listOfTopics.value.mapIndexed { clickedItemIndex, item ->
                if (clickedItemIndex == itemsIndex) {
                    when (item.isSelected) {
                        true -> item.copy(isSelected = !item.isSelected)
                        false -> {
                            if (!maxSelectableTopicCount) {
                                item.copy(isSelected = !item.isSelected)
                            } else {
                                _uiEvent.emit(showMessage(string.subscribe_topic_max_sel))
                                item
                            }
                        }
                    }
                } else item
            }.also {
                listOfTopics.value = it
            }
        }
    }

    fun saveUserSubscribedTopics() {
        val topicsToSubscribe =
            listOfTopics.value.filter { it.isSelected }

        viewModelScope.launch(dispatchers.main) {
            when {
                listOfTopics.value.isNotEmpty() && topicsToSubscribe.count() < 1 -> _uiEvent.emit(showMessage(string.subscribe_topic_min_sel))
                else -> {
                    subscribeTopicManager.updateSubscriptionStatus(
                        ids = topicsToSubscribe.map { it.id }
                    ).collect(
                        loader = isSubscriptionInProgress,
                        onError = { _uiEvent.emit(showMessage(it)) },
                        onSuccess = { _uiEvent.emit(success()) },
                    )
                }
            }
        }
    }

    fun refresh() {
        listOfTopics.value = emptyList()
        viewModelScope.launch(dispatchers.main) {
            getInExploreTopics.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    companion object {
        const val USER_AUTH_KEY = "slime_is_user_authenticated"
    }
}
