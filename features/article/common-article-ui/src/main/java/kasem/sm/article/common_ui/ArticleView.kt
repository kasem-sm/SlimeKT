/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.common_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.article.domain.model.Article

@Composable
fun ArticleView(
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    index: Int,
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

    saveScrollPosition(index)
}
