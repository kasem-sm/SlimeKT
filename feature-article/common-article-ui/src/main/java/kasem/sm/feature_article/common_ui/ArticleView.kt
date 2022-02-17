/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.common_ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.common_ui.SlimeElevatedButton
import kasem.sm.feature_article.domain.model.Article

@Composable
fun BoxScope.ArticleView(
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    index: Int,
    executeNextPage: () -> Unit,
    saveScrollPosition: (Int) -> Unit,
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageSize: Int,
    isLoading: Boolean,
    endOfPagination: Boolean
) {
    ArticleCard(
        modifier = modifier,
        article = article,
        imageLoader = imageLoader,
        onArticleClick = onArticleClick,
        index = index
    )

    if ((index + 1) >= (currentPage * pageSize) &&
        !isLoading &&
        !endOfPagination
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
