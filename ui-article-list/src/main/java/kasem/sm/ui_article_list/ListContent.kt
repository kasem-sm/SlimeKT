/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.feature_article.common_ui.emptyArticleView
import kasem.sm.ui_article_list.components.ArticleView
import kasem.sm.ui_article_list.components.SubscribeView

@Composable
internal fun ListContent(
    viewState: ListViewState,
    imageLoader: ImageLoader,
    onRefresh: () -> Unit,
    onArticleClick: (Int) -> Unit,
    executeNextPage: () -> Unit,
    updateSubscription: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    state: LazyListState,
) {
    SlimeSwipeRefresh(
        refreshing = viewState.isLoading,
        onRefresh = onRefresh
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SlimeScreenColumn(state) {
                item {
                    if (viewState.category != null) {
                        SubscribeView(
                            viewState = viewState,
                            updateSubscription = updateSubscription
                        )
                    }
                }

                item {
                    if (
                        !viewState.isLoading &&
                        viewState.endOfPagination &&
                        viewState.articles.isEmpty()
                    ) {
                        emptyArticleView { }
                    }
                }

                itemsIndexed(viewState.articles) { index, article ->
                    ArticleView(
                        article = article,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        index = index,
                        viewState = viewState,
                        executeNextPage = executeNextPage,
                        saveScrollPosition = saveScrollPosition
                    )
                }
            }
        }
    }
}
