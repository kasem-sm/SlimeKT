/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.feature_category.domain.interactors.GetAllCategories
import kasem.sm.feature_category.domain.interactors.ObserveAllCategories
import kasem.sm.feature_category.domain.model.Category
import kasem.sm.feature_category.worker.SubscribeCategoryManager
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.navigate
import kasem.sm.ui_core.showMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SubscribeCategoryViewModel @Inject constructor(
    private val getAllCategories: GetAllCategories,
    private val subscribeCategoryManager: SubscribeCategoryManager,
    observeAllCategories: ObserveAllCategories,
    private val slimeDispatchers: SlimeDispatchers
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    val loadingStatus = ObservableLoader()

    val listOfCategories = MutableStateFlow(emptyList<Category>())

    init {
        viewModelScope.launch {
            observeAllCategories.flow.collect {
                listOfCategories.value = it
            }
        }

        observeAllCategories.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        refresh()
    }

    fun updateList(itemsIndex: Int) {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            val isMoreThan3 = listOfCategories.value.count { it.isSelected } == 4

            listOfCategories.value.mapIndexed { clickedItemIndex, item ->
                if (clickedItemIndex == itemsIndex) {
                    when (item.isSelected) {
                        true -> item.copy(isSelected = !item.isSelected)
                        false -> {
                            if (!isMoreThan3) {
                                item.copy(isSelected = !item.isSelected)
                            } else {
                                _uiEvent.emit(showMessage("You can only select 4 topics for recommendation"))
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

        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            when {
//                categoriesToSubscribe.count() < 3 -> _uiEvent.emit(showMessage(string.minimum_subscription_msg))
                else -> {
                    subscribeCategoryManager.updateSubscriptionStatus(
                        ids = categoriesToSubscribe.map { it.id }
                    ).collect(
                        loader = loadingStatus,
                        onError = { _uiEvent.emit(showMessage(it)) },
                        onSuccess = { _uiEvent.emit(navigate(Routes.HomeScreen.route)) },
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            getAllCategories.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }
}
