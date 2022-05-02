/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.common_ui.EmptyView
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.ui_article_list.components.SubscribeView

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ListContent(
    state: ListState,
    imageLoader: ImageLoader,
    onRefresh: () -> Unit,
    onArticleClick: (Int) -> Unit,
    updateSubscription: () -> Unit,
    showAuthenticationSheet: () -> Unit,
    onBookmarkClick: (Int) -> Unit,
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
                if (state.topic != null) {
                    stickyHeader {
                        SubscribeView(
                            state = state,
                            updateSubscription = updateSubscription,
                            showAuthenticationSheet = showAuthenticationSheet,
                        )
                    }
                }

                if (
                    !state.isLoading &&
                    state.articles.isEmpty()
                ) {
                    item {
                        EmptyView(
                            modifier = Modifier
                                .semantics { testTag = "emptyView" },
                        )
                    }
                }

                items(state.articles) { article ->
                    ArticleCard(
                        article = article,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        onBookmarkClick = onBookmarkClick
                    )
                }
            }
        }
    }
}
