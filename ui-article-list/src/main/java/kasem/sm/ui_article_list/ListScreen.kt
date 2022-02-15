/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    navigateTo: (String) -> Unit,
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(ListState.EMPTY)

    val state = rememberLazyListState()

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onDataReceived = { position ->
            state.animateScrollToItem(position as Int)
        },
        onRouteReceived = navigateTo
    )

    ListContent(
        viewState = viewState,
        onRefresh = viewModel::refresh,
        imageLoader = imageLoader,
        onArticleClick = onArticleClick,
        executeNextPage = viewModel::executeNextPage,
        saveScrollPosition = viewModel::saveScrollPosition,
        state = state,
        updateSubscription = viewModel::updateSubscription,
        showAuthenticationSheet = {
            viewModel.checkAuthenticationStatus()
        }
    )
}
