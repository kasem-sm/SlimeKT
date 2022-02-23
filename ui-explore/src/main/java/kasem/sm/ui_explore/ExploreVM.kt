/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.article.domain.interactors.GetLatestArticles
import kasem.sm.article.domain.interactors.ObserveLatestArticles
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.topic.domain.interactors.GetInExploreTopics
import kasem.sm.topic.domain.interactors.ObserveInExploreTopics
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreVM @Inject constructor(
    private val getLatestArticles: GetLatestArticles,
    private val getInExploreTopics: GetInExploreTopics,
    observeLatestArticles: ObserveLatestArticles,
    observeInExploreTopics: ObserveInExploreTopics,
    private val slimeDispatchers: SlimeDispatchers
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    val state: StateFlow<ExploreState> = combine(
        loadingStatus.flow,
        observeLatestArticles.flow,
        observeInExploreTopics.flow
    ) { latestArticleLoading, latestArticles, topics ->
        ExploreState(
            isLoading = latestArticleLoading,
            articles = latestArticles,
            topics = topics
        )
    }.stateIn(viewModelScope, ExploreState.EMPTY)

    init {
        observeLatestArticles.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeInExploreTopics.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        refresh()
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.main) {
            getLatestArticles.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
        viewModelScope.launch(slimeDispatchers.main) {
            getInExploreTopics.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }
}
