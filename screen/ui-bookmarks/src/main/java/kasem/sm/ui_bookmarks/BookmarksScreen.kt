/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.ui_core.rememberStateWithLifecycle

@Composable
fun BookmarksScreen(
    viewModel: BookmarksVM,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
) {
    val articles by rememberStateWithLifecycle(viewModel.bookmarks)

    BookmarksContent(
        bookmarkedArticles = articles,
        imageLoader = imageLoader,
        onArticleClick = onArticleClick,
        onBookmarkClick = viewModel::updateBookmarkStatus,
        resetAllBookmarks = viewModel::resetAllBookmarks
    )
}
