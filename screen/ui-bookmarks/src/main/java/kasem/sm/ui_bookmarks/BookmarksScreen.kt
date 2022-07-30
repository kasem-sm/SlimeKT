/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import com.ramcosta.composedestinations.annotation.Destination
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle

@Destination
@Composable
fun BookmarksScreen(
    viewModel: BookmarksVM,
    imageLoader: ImageLoader,
    navigator: CommonNavigator
) {
    val articles by rememberStateWithLifecycle(viewModel.bookmarks)

    BookmarksContent(
        bookmarkedArticles = articles,
        imageLoader = imageLoader,
        onArticleClick = {
            navigator.navigateEvent(NavigationEvent.Detail(it))
        },
        onBookmarkClick = viewModel::updateBookmarkStatus,
        resetAllBookmarks = viewModel::resetAllBookmarks
    )
}
