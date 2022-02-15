/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.slime.ui_home.HomeState
import kasem.sm.common_ui.SlimeElevatedButton
import kasem.sm.feature_article.common_ui.ArticleCard
import kasem.sm.feature_article.domain.interactors.ArticlePager.Companion.PAGE_SIZE
import kasem.sm.feature_article.domain.model.Article

@Composable
internal fun BoxScope.ArticleView(
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    index: Int,
    state: HomeState,
    executeNextPage: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ArticleCard(
        modifier = modifier,
        article = article,
        imageLoader = imageLoader,
        onArticleClick = onArticleClick,
        index = index
    )

    if ((index + 1) >= (state.currentPage * PAGE_SIZE) &&
        !state.isLoading &&
        !state.endOfPagination
    ) {
        SlimeElevatedButton(
            text = "See More",
            onClick = {
                executeNextPage()
            },
            modifier = Modifier.align(Alignment.Center)
        )
    }

    saveScrollPosition(index)
}
