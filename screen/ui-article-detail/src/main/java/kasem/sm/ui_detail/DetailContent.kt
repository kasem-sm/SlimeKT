/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil.ImageLoader
import kasem.sm.article.domain.model.Article
import kasem.sm.article.markdown.markdown.SlimeMarkdown
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.common_ui.TopicChip
import kasem.sm.dynamic_links_handler.SLIME_DYNAMIC_LINK
import kasem.sm.dynamic_links_handler.generateSharingLink
import kasem.sm.ui_detail.components.ArticleAuthorAndEstimatedTimeBadge
import kasem.sm.ui_detail.components.ArticleFeaturedImage
import kasem.sm.ui_detail.components.ArticleHeader
import kasem.sm.ui_detail.components.slimeMarkdownStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DetailContent(
    imageLoader: ImageLoader,
    state: DetailState,
    snackbarHostState: SnackbarHostState,
    onRefresh: () -> Unit,
) {
    SlimeSwipeRefresh(
        refreshing = state.isLoading,
        onRefresh = onRefresh
    ) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SlimeScreenColumn {
                state.article?.let { article ->
                    stickyHeader {
                        ArticleHeader(
                            articleTitle = article.title,
                            onShareClick = {
                                context.onShareButtonClicked(
                                    article = article,
                                    scope = coroutineScope,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        )
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

fun Context.onShareButtonClicked(
    article: Article,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    generateSharingLink(
        deepLink = "$SLIME_DYNAMIC_LINK/article/${article.id}".toUri(),
        previewImageLink = article.featuredImage.toUri(),
        onFailure = {
            Timber.d("Generate Sharing Link Failed ${it.message}")
            scope.launch {
                snackbarHostState.showSnackbar(
                    getString(string.common_error_msg)
                )
            }
        },
        onSuccess = { generatedLink ->
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_msg_txt))
                putExtra(Intent.EXTRA_TEXT, generatedLink)
            }.also { startActivity(it) }
        }
    )
}
