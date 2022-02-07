/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.PaginationStage
import kasem.sm.core.domain.SlimePaginationStatus
import kasem.sm.core.domain.SlimePaginator
import kasem.sm.core.utils.toMessage
import kasem.sm.feature_article.domain.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion

/**
 * This class is responsible to paginate the list
 * of articles and restore it after process death
 * with the help of the viewModel.
 */
class ArticlePager @Inject constructor(
    private val getPagedArticles: GetPagedArticles,
    private val getArticlesTillPage: GetArticlesTillPage,
) {
    private lateinit var slimePaginator: SlimePaginator<List<Article>>

    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
    val articles = _articles.asStateFlow()

    private val _endOfPagination = MutableStateFlow(false)
    val endOfPagination = _endOfPagination.asStateFlow()

    // TODO: check if this actually works or not
    val loadingStatus = ObservableLoader()

    /**
     * @param category the current category query of the article shown
     * @param query the current search query of the article shown
     * @param page 0 at the time of initialization
     * @param scrollPosition Used to determine if process death has occurred or not
     * @param saveLoadedPage contains the latest loaded page number as to save it in SavedState
     * @param onError lambda gets triggered on any error during pagination
     * @param onRestorationComplete lambda gets triggered after articles restoration has completed
     */
    suspend fun initialize(
        category: String = "",
        query: String = "",
        page: Int,
        scrollPosition: Int,
        saveLoadedPage: (Int) -> Unit,
        onError: suspend (String) -> Unit,
        onRestorationComplete: suspend () -> Unit,
    ) {
        slimePaginator = SlimePaginator(
            requestForNextPage = { nextPage ->
                getPagedArticles.execute(category, query, nextPage, PAGE_SIZE)
            },
            currentStatus = { status ->
                when (status) {
                    is SlimePaginationStatus.EndOfPaginationStatus ->
                        _endOfPagination.value =
                            status.value
                    is SlimePaginationStatus.OnError -> onError(status.error.toMessage)
                    is SlimePaginationStatus.OnLoadingStatus -> loadingStatus(status.value)
                    is SlimePaginationStatus.OnPageLoaded -> {
                        if (scrollPosition == 0) {
                            saveLoadedPage(status.page)
                            _articles.value += status.data
                        }
                    }
                }
            },
            page = page
        )

        if (scrollPosition != 0) {
            restore(page, category, query, onRestorationComplete, onError)
        }
    }

    suspend fun executeNextPage(updatedPage: Int? = null) {
        slimePaginator.execute(updatedPage)
    }

    suspend fun refresh() {
        _articles.value = emptyList()
        executeNextPage(0)
    }

    /**
     * @param onRestorationComplete here we send the scroll position
     * retrieved from SavedState to the UI and reset it's value after that.
     */
    private suspend fun restore(
        page: Int,
        category: String = "",
        query: String = "",
        onRestorationComplete: suspend () -> Unit,
        onError: suspend (String) -> Unit,
    ) {
        getArticlesTillPage.execute(
            Param(category, query, page, PAGE_SIZE)
        ).onCompletion {
            onRestorationComplete()
        }.collect { stage ->
            when (stage) {
                is PaginationStage.Success -> _articles.value = stage.data
                is PaginationStage.Exception -> onError(stage.error.toMessage)
                else -> Unit
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
