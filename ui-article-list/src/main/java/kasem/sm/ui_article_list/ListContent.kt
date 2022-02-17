/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.feature_article.common_ui.ArticleView
import kasem.sm.feature_article.common_ui.emptyArticleView
import kasem.sm.feature_article.domain.interactors.ArticlePager
import kasem.sm.ui_article_list.components.SubscribeView

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ListContent(
    state: ListState,
    imageLoader: ImageLoader,
    onRefresh: () -> Unit,
    onArticleClick: (Int) -> Unit,
    executeNextPage: () -> Unit,
    updateSubscription: (onSuccess: () -> Unit) -> Unit,
    showAuthenticationSheet: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    listState: LazyListState,
) {
    SlimeSwipeRefresh(
        refreshing = state.isLoading,
        onRefresh = onRefresh
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SlimeScreenColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    if (state.category != null) {
                        SubscribeView(
                            state = state,
                            updateSubscription = updateSubscription,
                            showAuthenticationSheet = showAuthenticationSheet,
                            isUserAuthenticated = state.isUserAuthenticated
                        )
                    }
                }

                item {
                    if (
                        !state.isLoading &&
                        state.endOfPagination &&
                        state.articles.isEmpty()
                    ) {
                        emptyArticleView { }
                    }
                }

                itemsIndexed(state.articles) { index, article ->
                    ArticleView(
                        modifier = Modifier.animateItemPlacement(tween(500)),
                        article = article,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        index = index,
                        executeNextPage = executeNextPage,
                        saveScrollPosition = saveScrollPosition,
                        currentPage = state.currentPage,
                        pageSize = ArticlePager.PAGE_SIZE,
                        isLoading = state.isLoading,
                        endOfPagination = state.endOfPagination
                    )
                }
            }
        }
    }
}
