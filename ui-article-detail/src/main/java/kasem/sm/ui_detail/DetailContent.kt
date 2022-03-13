/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import kasem.sm.article.markdown.markdown.SlimeMarkdown
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.topic.common_ui.TopicChip
import kasem.sm.ui_detail.components.ArticleAuthorAndEstimatedTimeBadge
import kasem.sm.ui_detail.components.ArticleFeaturedImage
import kasem.sm.ui_detail.components.ArticleHeader
import kasem.sm.ui_detail.components.slimeMarkdownStyle

@OptIn(ExperimentalFoundationApi::class)
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
                    stickyHeader {
                        ArticleHeader(article)
                    }

                    item {
                        TopicChip(topic = article.topic)
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
