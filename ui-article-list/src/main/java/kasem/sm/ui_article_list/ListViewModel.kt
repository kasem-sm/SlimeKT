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
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.ObservableLoader.Companion.Loader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.core.interfaces.Session
import kasem.sm.feature_article.domain.interactors.ArticlePager
import kasem.sm.feature_category.domain.interactors.GetCategoryById
import kasem.sm.feature_category.domain.interactors.ObserveCategoryById
import kasem.sm.feature_category.worker.SubscribeCategoryManager
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
class ListViewModel @Inject constructor(
    private val pager: ArticlePager,
    private val getCategory: GetCategoryById,
    private val subscribeCategoryManager: SubscribeCategoryManager,
    private val savedStateHandle: SavedStateHandle,
    private val slimeDispatchers: SlimeDispatchers,
    private val session: Session,
    observeCategory: ObserveCategoryById,
) : ViewModel() {

    private val categoryId = savedStateHandle.get<String>(CATEGORY_ID_KEY)!!

    private val categoryQuery = savedStateHandle.get<String>(CATEGORY_QUERY_KEY)!!

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

    private val categoryLoadingStatus = ObservableLoader()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val state: StateFlow<ListState> = combineFlows(
        currentPage.flow,
        pager.loadingStatus.flow,
        categoryLoadingStatus.flow,
        pager.endOfPagination,
        pager.articles,
        observeCategory.flow,
        isSubscriptionInProgress.flow,
        isUserAuthenticated.flow
    ) { currentPage, paginationLoadStatus, categoryLoadStatus, endOfPagination,
        articles, category, isSubscriptionInProgress, isUserAuthenticated ->
        ListState(
            currentPage = currentPage,
            isLoading = paginationLoadStatus || categoryLoadStatus,
            endOfPagination = endOfPagination,
            articles = articles,
            category = category,
            isSubscriptionInProgress = isSubscriptionInProgress,
            isUserAuthenticated = isUserAuthenticated
        )
    }.stateIn(viewModelScope, ListState.EMPTY)

    init {
        viewModelScope.launch(slimeDispatchers.main) {
            session.observeAuthenticationState().collectLatest {
                isUserAuthenticated.value = it
            }
        }

        initializePager()

        observeCategory.join(
            params = categoryId,
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    private fun initializePager(
        categoryQuery: String = this.categoryQuery,
    ) {
        viewModelScope.launch(slimeDispatchers.main) {
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

        viewModelScope.launch(slimeDispatchers.main) {
            getCategory.execute(categoryId).collect(
                loader = categoryLoadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun updateSubscription(
        onSuccess: () -> Unit
    ) {
        isSubscriptionInProgress.invoke(Loader.START)
        viewModelScope.launch(slimeDispatchers.main) {
            subscribeCategoryManager.updateSubscriptionStatus(
                ids = listOf(categoryId)
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
        const val CATEGORY_QUERY_KEY = "slime_category"
        const val CATEGORY_ID_KEY = "slime_category_id"
    }
}
