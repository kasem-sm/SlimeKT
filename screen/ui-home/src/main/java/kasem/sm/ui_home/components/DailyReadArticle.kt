/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.article.domain.model.Article

@Composable
internal fun DailyReadArticle(
    article: Article?,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    article?.let {
        ArticleCard(
            article = it,
            imageLoader = imageLoader,
            onArticleClick = onArticleClick,
            onBookmarkClick = onBookmarkClick,
            modifier = modifier
        )
    }
}
