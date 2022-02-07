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
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.feature_article.domain.interactors.ArticlePager
import kasem.sm.feature_category.domain.interactors.GetCategoryById
import kasem.sm.feature_category.domain.interactors.ObserveCategoryById
import kasem.sm.feature_category.worker.SubscribeCategoryManager
import kasem.sm.ui_article_list.ListViewState.Companion.DEFAULT_CATEGORY_QUERY
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListViewModel @Inject constructor(
    private val pager: ArticlePager,
    private val getCategory: GetCategoryById,
    private val subscribeCategoryManager: SubscribeCategoryManager,
    observeCategory: ObserveCategoryById,
    private val savedStateHandle: SavedStateHandle,
    private val slimeDispatchers: SlimeDispatchers
) : ViewModel() {

    private val categoryId = savedStateHandle.get<String>(CATEGORY_ID_KEY)!!

    private val categoryQuery = SavedMutableState(
        savedStateHandle,
        CATEGORY_QUERY_KEY,
        defaultValue = DEFAULT_CATEGORY_QUERY
    )

    private val scrollPosition = SavedMutableState(
        savedStateHandle,
        LIST_POSITION_KEY,
        defaultValue = 0
    )

    private val currentPage = SavedMutableState(
        savedStateHandle,
        PAGE_KEY,
        defaultValue = 0
    )

    private val loadingStatus = ObservableLoader()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val state: StateFlow<ListViewState> = combineFlows(
        currentPage.flow,
        pager.loadingStatus.flow,
        pager.endOfPagination,
        pager.articles,
        observeCategory.flow,
        loadingStatus.flow
    ) { currentPage, isLoading, endOfPagination, articles, category, isSubscriptionInProgress ->
        ListViewState(
            currentPage = currentPage,
            isLoading = isLoading,
            endOfPagination = endOfPagination,
            articles = articles,
            category = category,
            isSubscriptionInProgress = isSubscriptionInProgress
        )
    }.stateIn(viewModelScope, ListViewState.EMPTY)

    init {
        initializePager()

        observeCategory.join(
            params = categoryId,
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    private fun initializePager(
        categoryQuery: String = this.categoryQuery.value,
    ) {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            pager.initialize(
                category = categoryQuery,
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
                refresh()
            }
        }
    }

    fun executeNextPage(updatedPage: Int? = null) {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            pager.executeNextPage(updatedPage)
        }
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            pager.refresh()
        }

        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            getCategory.execute(categoryId).collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun updateSubscription() {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            subscribeCategoryManager.updateSubscriptionStatus(
                ids = listOf(categoryId)
            ).collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
                onSuccess = { _uiEvent.emit(showMessage(kasem.sm.common_ui.R.string.common_success_msg)) },
            )
        }
    }

    /**
     * Saves the latest scroll position to the savedState
     * as after process death, we will retrieve it from savedState
     * to scroll the list to the position for best user experience.
     */
    fun saveScrollPosition(updatedPosition: Int) {
        savedStateHandle.set(LIST_POSITION_KEY, updatedPosition)
    }

    companion object {
        const val LIST_POSITION_KEY = "slime_list_position"
        const val PAGE_KEY = "slime_page"
        const val CATEGORY_QUERY_KEY = "slime_category"
        const val CATEGORY_ID_KEY = "slime_category_id"
    }
}
