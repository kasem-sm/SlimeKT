/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.common_ui.util.Destination
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@Composable
fun HomeScreen(
    viewModel: HomeVM,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    navigateTo: (String) -> Unit,
    backHandler: @Composable (enabled: Boolean, onBack: () -> Unit) -> Unit
) {
    val state by rememberStateWithLifecycle(viewModel.state)

    val listState = rememberLazyListState()

    val isBackHandlerEnabled = state.currentQuery.isNotEmpty()

    backHandler(
        enabled = isBackHandlerEnabled,
        onBack = {
            if (isBackHandlerEnabled) {
                viewModel.onQueryChange(HomeState.DEFAULT_SEARCH_QUERY)
            }
        }
    )

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onDataReceived = { position ->
            listState.animateScrollToItem(position as Int)
        },
        onRouteReceived = { navigateTo(it) }
    )

    HomeContent(
        state = state,
        imageLoader = imageLoader,
        onRefresh = viewModel::refresh,
        onQueryChange = viewModel::onQueryChange,
        onTopicChange = viewModel::onQueryChange,
        onArticleClick = onArticleClick,
        navigateToSubscriptionScreen = {
            navigateTo(Destination.SubscribeTopicScreen.route)
        },
        onBookmarkClick = viewModel::updateBookmarkStatus,
        listState = listState
    )
}
