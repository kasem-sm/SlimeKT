/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.article.domain.model.Article

internal fun LazyListScope.discoverArticlesView(
    articles: List<Article>,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit
) {
    items(articles) { article ->
        ArticleCard(
            article = article,
            imageLoader = imageLoader,
            onArticleClick = onArticleClick,
            onBookmarkClick = onBookmarkClick
        )
    }
}
