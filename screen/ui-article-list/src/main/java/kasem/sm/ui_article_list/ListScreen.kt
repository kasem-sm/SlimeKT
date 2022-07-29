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
import com.ramcosta.composedestinations.annotation.Destination
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

data class ListScreenArgs(
    val topicId: String,
    val topicQuery: String
)

@Destination(navArgsDelegate = ListScreenArgs::class)
@Composable
fun ListScreen(
    viewModel: ListVM,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    navigator: CommonNavigator,
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    val state = rememberLazyListState()

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onDataReceived = { position ->
            state.animateScrollToItem(position as Int)
        },
        onNavigate = navigator::navigateEvent
    )

    ListContent(
        state = viewState,
        imageLoader = imageLoader,
        onRefresh = viewModel::refresh,
        onArticleClick = { navigator.navigateEvent(NavigationEvent.Detail(id = it)) },
        updateSubscription = viewModel::updateSubscription,
        showAuthenticationSheet = viewModel::checkAuthenticationStatus,
        onBookmarkClick = viewModel::updateBookmarkStatus,
        listState = state
    )
}
