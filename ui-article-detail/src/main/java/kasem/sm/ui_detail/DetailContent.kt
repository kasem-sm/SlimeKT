/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.article.markdown.markdown.SlimeMarkdown
import kasem.sm.category.common_ui.CategoryChip
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.ui_detail.components.ArticleAuthorAndEstimatedTimeBadge
import kasem.sm.ui_detail.components.ArticleFeaturedImage
import kasem.sm.ui_detail.components.ArticleHeader
import kasem.sm.ui_detail.components.slimeMarkdownStyle

@Composable
internal fun DetailContent(
    imageLoader: ImageLoader,
    state: DetailState,
    onRefresh: () -> Unit,
) {
    SlimeSwipeRefresh(
        refreshing = state.isLoading,
        onRefresh = onRefresh
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SlimeScreenColumn {
                state.article?.let { article ->
                    item {
                        ArticleHeader(article)
                    }

                    item {
                        CategoryChip(category = article.category)
                    }

                    item {
                        ArticleAuthorAndEstimatedTimeBadge(article)
                    }

                    item {
                        imageLoader.ArticleFeaturedImage(article.featuredImage)
                    }

                    item {
                        SlimeMarkdown(
                            text = article.description,
                            markdownStyle = slimeMarkdownStyle()
                        )
                    }
                }
            }
        }
    }
}
