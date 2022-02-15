/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list.components

import androidx.compose.runtime.Composable
import coil.ImageLoader
import kasem.sm.feature_article.common_ui.ArticleCard
import kasem.sm.feature_article.domain.interactors.ArticlePager.Companion.PAGE_SIZE
import kasem.sm.feature_article.domain.model.Article
import kasem.sm.ui_article_list.ListState

@Composable
internal fun ArticleView(
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    index: Int,
    state: ListState,
    executeNextPage: () -> Unit,
    saveScrollPosition: (Int) -> Unit
) {
    ArticleCard(
        article = article,
        imageLoader = imageLoader,
        onArticleClick = onArticleClick
    )

    if ((index + 1) >= (state.currentPage * PAGE_SIZE) &&
        !state.isLoading &&
        !state.endOfPagination
    ) {
        executeNextPage()
    }

    saveScrollPosition(index)
}
