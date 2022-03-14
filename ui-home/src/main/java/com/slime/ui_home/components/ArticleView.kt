/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.slime.ui_home.HomeState
import kasem.sm.article.common_ui.ArticleView
import kasem.sm.article.domain.interactors.ArticlePager.Companion.PAGE_SIZE
import kasem.sm.article.domain.model.Article

@Composable
internal fun BoxScope.ArticleView(
    modifier: Modifier = Modifier,
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    index: Int,
    executeNextPage: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    state: HomeState
) {
    ArticleView(
        modifier = modifier,
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
