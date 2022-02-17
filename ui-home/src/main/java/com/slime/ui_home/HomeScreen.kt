/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.common_ui.util.Routes
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    navigateTo: (String) -> Unit
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(HomeState.EMPTY)

    val state = rememberLazyListState()

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onDataReceived = { position ->
            state.animateScrollToItem(position as Int)
        },
        onRouteReceived = { navigateTo(it) }
    )

    HomeContent(
        state = viewState,
        imageLoader = imageLoader,
        onRefresh = viewModel::refresh,
        onQueryChange = viewModel::onQueryChange,
        onCategoryChange = viewModel::onCategoryChange,
        onArticleClick = onArticleClick,
        executeNextPage = viewModel::executeNextPage,
        saveScrollPosition = viewModel::saveScrollPosition,
        listState = state,
        navigateToSubscriptionScreen = {
            navigateTo(Routes.SubscribeCategoryScreen.route)
        }
    )
}
