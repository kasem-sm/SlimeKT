/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

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
import com.slime.ui_home.components.ArticleView
import com.slime.ui_home.components.CategoriesRow
import com.slime.ui_home.components.SearchBar
import kasem.sm.common_ui.SlimeHeader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.feature_article.common_ui.ArticleCard
import kasem.sm.feature_article.common_ui.emptyArticleView

@Composable
internal fun HomeContent(
    viewState: HomeViewState,
    imageLoader: ImageLoader,
    onRefresh: () -> Unit,
    onQueryChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onArticleClick: (Int) -> Unit,
    executeNextPage: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    navigateToSubscriptionScreen: () -> Unit,
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
            SlimeScreenColumn(
                state = state,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchBar(
                        query = viewState.currentQuery,
                        onQueryChange = onQueryChange,
                        onSearchActionClicked = onRefresh,
                        onTrailingIconClicked = { onQueryChange(HomeViewState.DEFAULT_CATEGORY_QUERY) },
                        placeholders = listOf(
                            "Search with article's title",
                            "Search with author's name",
                        )
                    )
                }

                item {
                    CategoriesRow(
                        categories = viewState.categories,
                        currentCategory = viewState.currentCategory,
                        onCategoryChange = onCategoryChange,
                        navigateToSubscriptionScreen = navigateToSubscriptionScreen
                    )
                }

                item {
                    if (
                        !viewState.isLoading &&
                        viewState.endOfPagination &&
                        viewState.articles.isEmpty()
                    ) {
                        emptyArticleView {
                        }
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

                item {
                    SlimeHeader(text = "Daily Read")
                }

                item {
                    viewState.dailyReadArticle?.let {
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
