/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kasem.sm.article.domain.interactors.BookmarkArticle
import kasem.sm.article.domain.interactors.GetInExploreArticles
import kasem.sm.article.domain.observers.ObserveInExploreArticles
import kasem.sm.auth_api.ObserveAuthState
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.topic.domain.interactors.GetInExploreTopics
import kasem.sm.topic.domain.observers.ObserveInExploreTopics
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreVM @Inject constructor(
    private val getArticles: GetInExploreArticles,
    private val getTopics: GetInExploreTopics,
    private val bookmarkArticle: BookmarkArticle,
    observeArticles: ObserveInExploreArticles,
    observeTopics: ObserveInExploreTopics,
    private val dispatchers: SlimeDispatchers,
    private val observeAuthState: ObserveAuthState,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    val state: StateFlow<ExploreState> = combine(
        loadingStatus.flow,
        observeArticles.flow,
        observeTopics.flow
    ) { latestArticleLoading, latestArticles, topics ->
        ExploreState(
            isLoading = latestArticleLoading,
            articles = latestArticles,
            topics = topics
        )
    }.stateIn(viewModelScope, ExploreState.EMPTY)

    init {
        viewModelScope.launch(dispatchers.main) {
            observeAuthState.joinAndCollect(
                coroutineScope = viewModelScope
            ).collectLatest {
                refresh()
            }
        }

        observeArticles.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )

        observeTopics.join(
            coroutineScope = viewModelScope,
            onError = { _uiEvent.emit(showMessage(it)) },
        )
    }

    fun refresh() {
        viewModelScope.launch(dispatchers.main) {
            getArticles.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }

        viewModelScope.launch(dispatchers.main) {
            getTopics.execute().collect(
                loader = loadingStatus,
                onError = { _uiEvent.emit(showMessage(it)) },
            )
        }
    }

    fun updateBookmarkStatus(articleId: Int) {
        viewModelScope.launch {
            bookmarkArticle.execute(articleId).collect()
        }
    }
}
