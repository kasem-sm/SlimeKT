/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.runtime.Composable
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.article.domain.model.Article

@Composable
internal fun DailyReadArticle(
    article: Article?,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit,
) {
    article?.let {
        ArticleCard(
            article = it,
            imageLoader = imageLoader,
            onArticleClick = onArticleClick,
            onBookmarkClick = onBookmarkClick
        )
    }
}
