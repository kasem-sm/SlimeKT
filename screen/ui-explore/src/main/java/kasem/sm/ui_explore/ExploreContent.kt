/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import kasem.sm.common_ui.EmptyView
import kasem.sm.common_ui.ProfileCard
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeHeader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.ui_explore.components.DiscoverTopicsView
import kasem.sm.ui_explore.components.discoverArticlesView

@Composable
internal fun ExploreContent(
    state: ExploreState,
    onRefresh: () -> Unit,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit,
    onTopicClick: (title: String, id: String) -> Unit,
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
                item {
                    SlimeHeader(text = stringResource(id = R.string.recommend_article_header))
                }

                if (!state.isLoading && state.articles.isEmpty()) {
                    item {
                        EmptyView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            emoji = "🤏",
                            message = "You are all set!"
                        )
                    }
                } else {
                    discoverArticlesView(
                        articles = state.articles,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        onBookmarkClick = onBookmarkClick
                    )
                }

                if (state.topics.isNotEmpty()) {
                    item {
                        SlimeHeader(text = stringResource(id = R.string.discover_topics_header))
                    }

                    item {
                        DiscoverTopicsView(state, onTopicClick)
                    }
                }

                item {
                    SlimeHeader(
                        text = stringResource(id = R.string.find_authors_header),
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                }

                item {
                    ProfileCard(
                        username = "Qasim",
                        bio = "This is an example bio of this user limited to 2 lines, it means that you can't see this text.",
                    )
                }
            }
        }
    }
}
