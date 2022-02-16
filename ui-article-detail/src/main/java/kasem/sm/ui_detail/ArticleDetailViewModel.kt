/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.feature_article.domain.interactors.GetArticleById
import kasem.sm.feature_article.domain.interactors.ObserveArticle
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticle: GetArticleById,
    private val observeArticle: ObserveArticle,
    private val slimeDispatchers: SlimeDispatchers,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val articleId = savedStateHandle.get<String>("id")?.toInt() ?: -1

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    val state: StateFlow<ArticleDetailState> = combine(
        loadingStatus.flow,
        observeArticle.flow
    ) { loading, article ->
        ArticleDetailState(
            isLoading = loading,
            article = article
        )
    }.stateIn(viewModelScope, ArticleDetailState.EMPTY)

    init {
        observe()

        refresh()
    }

    private fun observe(scope: CoroutineScope = viewModelScope) {
        observeArticle.join(
            coroutineScope = scope,
            onError = { _uiEvent.emit(showMessage(it)) },
            params = articleId,
        )
    }

    fun refresh() {
        viewModelScope.launch(slimeDispatchers.main) {
            getArticle.execute(articleId)
                .collect(
                    loader = loadingStatus,
                    onError = { _uiEvent.emit(showMessage(it)) },
                )
        }
    }
}
