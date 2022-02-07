/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.feature_article.domain.interactors.GetLatestArticles
import kasem.sm.feature_article.domain.interactors.ObserveLatestArticles
import kasem.sm.feature_category.domain.interactors.GetInExploreCategories
import kasem.sm.feature_category.domain.interactors.ObserveInExploreCategories
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getLatestArticles: GetLatestArticles,
    private val getInExploreCategories: GetInExploreCategories,
    observeLatestArticles: ObserveLatestArticles,
    observeInExploreCategories: ObserveInExploreCategories,
    private val slimeDispatchers: SlimeDispatchers
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    val state: StateFlow<ExploreViewState> = combine(
        loadingStatus.flow,
        observeLatestArticles.flow,
        observeInExploreCategories.flow
    ) { latestArticleLoading, latestArticles, categories ->
        ExploreViewState(
            isLoading = latestArticleLoading,
            articles = latestArticles,
            categories = categories
        )
    }.stateIn(viewModelScope, ExploreViewState.EMPTY)

    init {
        Timber.d("Init ExploreViewModel")

        observeLatestArticles.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeInExploreCategories.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        refresh()
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            getLatestArticles.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            getInExploreCategories.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }
}
