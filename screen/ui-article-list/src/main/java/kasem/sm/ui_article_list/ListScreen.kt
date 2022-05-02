/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@Composable
fun ListScreen(
    viewModel: ListVM,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    navigateTo: (String) -> Unit,
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    val state = rememberLazyListState()

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onDataReceived = { position ->
            state.animateScrollToItem(position as Int)
        },
        onRouteReceived = navigateTo
    )

    ListContent(
        state = viewState,
        imageLoader = imageLoader,
        onRefresh = viewModel::refresh,
        onArticleClick = onArticleClick,
        updateSubscription = viewModel::updateSubscription,
        showAuthenticationSheet = viewModel::checkAuthenticationStatus,
        onBookmarkClick = viewModel::updateBookmarkStatus,
        listState = state
    )
}
