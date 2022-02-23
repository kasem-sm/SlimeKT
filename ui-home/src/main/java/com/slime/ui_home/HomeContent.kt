/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

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
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import com.slime.ui_home.components.SearchBar
import com.slime.ui_home.components.TopicsRow
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.article.common_ui.ArticleView
import kasem.sm.article.common_ui.emptyArticleView
import kasem.sm.article.domain.interactors.ArticlePager.Companion.PAGE_SIZE
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeHeader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeContent(
    state: HomeState,
    imageLoader: ImageLoader,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onTopicChange: (String) -> Unit,
    onArticleClick: (Int) -> Unit,
    executeNextPage: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    navigateToSubscriptionScreen: () -> Unit,
    listState: LazyListState,
) {
    SlimeSwipeRefresh(
        refreshing = state.paginationLoadStatus,
        onRefresh = onRefresh
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SlimeScreenColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchBar(
                        query = state.currentQuery,
                        onQueryChange = onQueryChange,
                        onSearchActionClicked = { onRefresh() },
                        onTrailingIconClicked = { onQueryChange(HomeState.DEFAULT_TOPIC_QUERY) },
                        placeholders = listOf(
                            stringResource(id = R.string.article_search_txt_1),
                            stringResource(id = R.string.article_search_txt_2),
                        ),
                    )
                }

                item {
                    TopicsRow(
                        isLoading = state.isLoading,
                        topics = state.topics,
                        currentTopic = state.currentTopic,
                        onTopicChange = onTopicChange,
                        navigateToSubscriptionScreen = navigateToSubscriptionScreen
                    )
                }

                item {
                    if (
                        !state.paginationLoadStatus &&
                        state.endOfPagination &&
                        state.articles.isEmpty()
                    ) {
                        emptyArticleView {}
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
                        pageSize = PAGE_SIZE,
                        isLoading = state.paginationLoadStatus,
                        endOfPagination = state.endOfPagination,
                        onUserDemandPagination = true
                    )
                }

                item {
                    SlimeHeader(text = stringResource(id = R.string.daily_read_header))
                }

                item {
                    state.dailyReadArticle?.let {
                        ArticleCard(
                            article = it,
                            imageLoader = imageLoader,
                            onArticleClick = onArticleClick,
                        )
                    }
                }
            }
        }
    }
}
