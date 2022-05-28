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
import kasem.sm.article.domain.interactors.GetArticle
import kasem.sm.article.domain.observers.ObserveArticle
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.collect
import kasem.sm.topic.domain.observers.ObserveTopicByTitle
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel
class DetailVM @Inject constructor(
    private val getArticle: GetArticle,
    private val observeArticle: ObserveArticle,
    private val observeTopic: ObserveTopicByTitle,
    private val dispatchers: SlimeDispatchers,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val articleId = savedStateHandle.get<String>("id")?.toInt() ?: -1

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val loadingStatus = ObservableLoader()

    val state: StateFlow<DetailState> = combine(
        loadingStatus.flow,
        observeArticle.flow,
        observeTopic.flow,
    ) { loading, article, topic ->
        DetailState(
            isLoading = loading,
            article = article,
            topic = topic
        )
    }.stateIn(
        coroutineScope = viewModelScope + dispatchers.main,
        initialValue = DetailState.EMPTY
    )

    init {
        observe()

        refresh()
    }

    private fun observe() {
        viewModelScope.launch(dispatchers.main) {
            observeArticle.joinAndCollect(
                coroutineScope = viewModelScope + dispatchers.main,
                onError = {
                    _uiEvent.emit(showMessage(it))
                },
                params = articleId,
            ).collectLatest { article ->
                article?.let {
                    observeTopic.join(
                        params = it.topic,
                        coroutineScope = viewModelScope + dispatchers.main,
                        onError = { msg -> _uiEvent.emit(showMessage(msg)) },
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(dispatchers.main) {
            getArticle.execute(articleId)
                .collect(
                    loader = loadingStatus,
                    onError = {
                        _uiEvent.emit(showMessage(it))
                    },
                )
        }
    }
}
