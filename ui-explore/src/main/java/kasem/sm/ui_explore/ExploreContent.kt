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
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import kasem.sm.common_ui.SlimeFlowRow
import kasem.sm.common_ui.SlimeHeader
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.feature_article.common_ui.ArticleCard
import kasem.sm.ui_explore.components.CategoryView
import kasem.sm.ui_explore.components.ProfileCard

@Composable
internal fun ExploreContent(
    state: ExploreState,
    onRefresh: () -> Unit,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onCategoryClick: (title: String, id: String) -> Unit,
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
                    SlimeHeader(text = "Recommended Articles")
                }

                itemsIndexed(state.articles) { index, article ->
                    ArticleCard(
                        article = article,
                        imageLoader = imageLoader,
                        onArticleClick = onArticleClick,
                        index = index
                    )
                }

                if (state.categories.isNotEmpty()) {
                    item {
                        SlimeHeader(text = "Discover Categories")
                    }

                    item {
                        SlimeFlowRow(
                            mainAxisSpacing = 20.dp, crossAxisSpacing = 15.dp
                        ) {
                            state.categories.forEach { category ->
                                CategoryView(category, onCategoryClick)
                            }
                        }
                    }
                }

                item {
                    SlimeHeader(
                        text = "Find Authors",
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
