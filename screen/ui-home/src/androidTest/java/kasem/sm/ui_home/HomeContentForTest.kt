/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_home

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader

@SuppressLint("ComposableNaming")
@Composable
fun homeContentForTest(
    state: HomeState,
    onRefresh: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onTopicChange: (String) -> Unit = {},
    onArticleClick: (Int) -> Unit = {},
    navigateToSubscriptionScreen: () -> Unit = {},
    onBookmarkClick: (Int) -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {
    HomeContent(
        state = state,
        imageLoader = ImageLoader(LocalContext.current),
        onRefresh = onRefresh,
        onQueryChange = onQueryChange,
        onTopicChange = onTopicChange,
        onArticleClick = onArticleClick,
        navigateToSubscriptionScreen = navigateToSubscriptionScreen,
        onBookmarkClick = onBookmarkClick,
        listState = listState
    )
}
