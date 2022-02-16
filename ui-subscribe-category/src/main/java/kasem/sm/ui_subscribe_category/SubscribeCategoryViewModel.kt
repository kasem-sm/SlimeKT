/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.ObservableLoader.Companion.Loader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.core.interfaces.Session
import kasem.sm.feature_category.domain.interactors.GetInExploreCategories
import kasem.sm.feature_category.domain.interactors.ObserveInExploreCategories
import kasem.sm.feature_category.domain.model.Category
import kasem.sm.feature_category.worker.SubscribeCategoryManager
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.navigate
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class SubscribeCategoryViewModel @Inject constructor(
    /** Categories that are not subscribed can be requested through [getInExploreCategories] **/
    private val getInExploreCategories: GetInExploreCategories,
    private val subscribeCategoryManager: SubscribeCategoryManager,
    private val slimeDispatchers: SlimeDispatchers,
    private val session: Session,
    observeInExploreCategories: ObserveInExploreCategories,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    private val listOfCategories = MutableStateFlow(emptyList<Category>())

    private val isUserAuthenticated = SavedMutableState(
        savedStateHandle,
        "user_authenticated",
        defValue = false
    )

    val state: StateFlow<SubscribeCategoryState> = combine(
        loadingStatus.flow,
        listOfCategories,
        isUserAuthenticated.flow,
    ) { loadStatus, categories, isUserAuthenticated ->
        SubscribeCategoryState(
            isLoading = loadStatus,
            categories = categories,
            isUserAuthenticated = isUserAuthenticated
        )
    }.stateIn(viewModelScope, SubscribeCategoryState.EMPTY)

    init {
        viewModelScope.launch(slimeDispatchers.main) {
            session.observeAuthenticationState().collectLatest {
                isUserAuthenticated.value = it
            }
        }

        viewModelScope.launch(slimeDispatchers.main) {
            observeInExploreCategories.flow.collectLatest {
                listOfCategories.value = it
            }
        }

        observeInExploreCategories.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        refresh()
    }

    fun checkAuthenticationStatus() {
        viewModelScope.launch(slimeDispatchers.main) {
            if (!isUserAuthenticated.value) {
                _uiEvent.emit(navigate(Routes.RegisterScreen.route))
            }
        }
    }

    fun updateList(itemsIndex: Int) {
        viewModelScope.launch(slimeDispatchers.main) {
            val maxSelectableCategoryCount = listOfCategories.value.count { it.isSelected } == 5

            listOfCategories.value.mapIndexed { clickedItemIndex, item ->
                if (clickedItemIndex == itemsIndex) {
                    when (item.isSelected) {
                        true -> item.copy(isSelected = !item.isSelected)
                        false -> {
                            if (!maxSelectableCategoryCount) {
                                item.copy(isSelected = !item.isSelected)
                            } else {
                                _uiEvent.emit(showMessage("You can only select 5 topics for recommendation"))
                                item
                            }
                        }
                    }
                } else item
            }.also {
                listOfCategories.value = it
            }
        }
    }

    fun saveUserSubscribedCategories() {
        val categoriesToSubscribe =
            listOfCategories.value.filter { it.isSelected }

        viewModelScope.launch(slimeDispatchers.main) {
            when {
                categoriesToSubscribe.count() < 3 -> _uiEvent.emit(showMessage(string.minimum_subscription_msg))
                else -> {
                    loadingStatus(Loader.START)
                    subscribeCategoryManager.updateSubscriptionStatus(
                        ids = categoriesToSubscribe.map { it.id }
                    ).collect(
                        loader = loadingStatus,
                        onError = { _uiEvent.emit(showMessage(it)) },
                        onSuccess = { _uiEvent.emit(UiEvent.Success) },
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.main) {
            getInExploreCategories.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }
}
