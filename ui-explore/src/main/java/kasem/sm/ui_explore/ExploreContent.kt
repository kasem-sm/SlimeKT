/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeFlowRow
import kasem.sm.common_ui.SlimeHeader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.ui_explore.components.ProfileCard
import kasem.sm.ui_explore.components.TopicView

@Composable
internal fun ExploreContent(
    state: ExploreState,
    onRefresh: () -> Unit,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
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

                itemsIndexed(state.articles) { index, article ->
                    ArticleCard(
                        article = article,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        index = index
                    )
                }

                if (state.topics.isNotEmpty()) {
                    item {
                        SlimeHeader(text = stringResource(id = R.string.discover_topics_header))
                    }

                    item {
                        SlimeFlowRow(
                            mainAxisSpacing = 20.dp, crossAxisSpacing = 15.dp
                        ) {
                            state.topics.forEach { topic ->
                                TopicView(topic, onTopicClick)
                            }
                        }
                    }
                }

                item {
                    SlimeHeader(
                        text = stringResource(id = R.string.find_authors_header),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    )
                }

                item {
                    ProfileCard(
                        username = "Kasim",
                        bio = "This is an example bio of this user limited to 2 lines, it means that you can't see this text.",
                    )
                }
            }
        }
    }
}
