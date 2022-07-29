/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.common_ui.*
import kasem.sm.common_ui.R
import kasem.sm.common_ui.util.dynamicItem
import kasem.sm.ui_home.components.DailyReadArticle
import kasem.sm.ui_home.components.SearchBar
import kasem.sm.ui_home.components.topicsView

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeContent(
    state: HomeState,
    imageLoader: ImageLoader,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onTopicChange: (String) -> Unit,
    onArticleClick: (Int) -> Unit,
    navigateToSubscriptionScreen: () -> Unit,
    onBookmarkClick: (Int) -> Unit,
    listState: LazyListState,
) {
    SlimeSwipeRefresh(
        refreshing = state.isLoading,
        onRefresh = onRefresh,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 10.dp)
        ) {
            SlimeScreenColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                stickyHeader {
                    SearchBar(
                        query = state.currentQuery,
                        onQueryChange = onQueryChange,
                        onSearchActionClicked = { onRefresh() },
                        onTrailingIconClicked = { onQueryChange(HomeState.DEFAULT_TOPIC_QUERY) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .semantics { testTag = "home_search_bar" }
                    )
                }

                topicsView(
                    isLoading = state.isLoading,
                    isUserAuthenticated = state.isUserAuthenticated,
                    topics = state.topics,
                    currentTopic = state.currentQuery,
                    onTopicChange = onTopicChange,
                    navigateToSubscriptionScreen = navigateToSubscriptionScreen
                )

                item {
                    SlimeHeader(text = stringResource(id = R.string.daily_read_header))
                }

                item {
                    DailyReadArticle(
                        modifier = Modifier.semantics { testTag = "daily_read_article_view" },
                        article = state.dailyReadArticle,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        onBookmarkClick = onBookmarkClick
                    )
                }

                item {
                    SlimeHeader(text = "Latest\nArticles")
                }

                dynamicItem(!state.isLoading && state.articles.isEmpty()) {
                    EmptyView()
                }

                items(state.articles) { article ->
                    ArticleCard(
                        article = article,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        onBookmarkClick = onBookmarkClick,
                        modifier = Modifier.semantics { testTag = "articles_list_card" }
                    )
                }
            }
        }
    }
}
