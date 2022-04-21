/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_bookmarks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import coil.ImageLoader
import kasem.sm.article.common_ui.ArticleCard
import kasem.sm.article.domain.model.Article
import kasem.sm.common_ui.EmptyView
import kasem.sm.common_ui.SlimeHeader
import kasem.sm.common_ui.SlimeScreenColumn

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BookmarksContent(
    bookmarkedArticles: List<Article>,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit,
    resetAllBookmarks: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        var isExpanded by remember {
            mutableStateOf(false)
        }

        SlimeScreenColumn {
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        SlimeHeader(text = "Your\nBookmarks")
                    }

                    item {
                        Box {
                            val icon = Icons.Default.MoreVert
                            IconButton(
                                onClick = { isExpanded = true }
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = icon.name,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        resetAllBookmarks()
                                        isExpanded = false
                                    }
                                ) {
                                    Text(text = "Reset Bookmarks")
                                }
                            }
                        }
                    }
                }
            }

            if (bookmarkedArticles.isEmpty()) {
                item {
                    EmptyView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .semantics { testTag = "emptyView" },
                        emoji = "💔"
                    )
                }
            }

            items(bookmarkedArticles) { article ->
                ArticleCard(
                    article = article,
                    imageLoader = imageLoader,
                    onArticleClick = onArticleClick,
                    onBookmarkClick = onBookmarkClick
                )
            }
        }
    }
}
