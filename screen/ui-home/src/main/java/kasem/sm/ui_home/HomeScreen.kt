/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.ui_core.BackHandler
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun HomeScreen(
    viewModel: HomeVM,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    navigator: CommonNavigator,
    backHandler: BackHandler
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
        onNavigate = navigator::navigateEvent
    )

    HomeContent(
        state = state,
        imageLoader = imageLoader,
        onRefresh = viewModel::refresh,
        onQueryChange = viewModel::onQueryChange,
        onTopicChange = viewModel::onQueryChange,
        onArticleClick = { navigator.navigateEvent(NavigationEvent.Detail(it)) },
        navigateToSubscriptionScreen = {
            navigator.navigateEvent(NavigationEvent.Subscription)
        },
        onBookmarkClick = viewModel::updateBookmarkStatus,
        listState = listState
    )
}
