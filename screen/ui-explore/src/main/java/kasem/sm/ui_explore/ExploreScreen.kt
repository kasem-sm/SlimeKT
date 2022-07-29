/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import com.ramcosta.composedestinations.annotation.Destination
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector
import kotlinx.coroutines.FlowPreview

@Destination
@FlowPreview
@Composable
fun ExploreScreen(
    viewModel: ExploreVM,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    navigator: CommonNavigator,
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar
    )

    ExploreContent(
        state = viewState,
        onRefresh = viewModel::refresh,
        imageLoader = imageLoader,
        onArticleClick = {
            navigator.navigateEvent(NavigationEvent.Detail(it))
        },
        onTopicClick = { title, id ->
            navigator.navigateEvent(NavigationEvent.ListScreen(title = title, id = id))
        },
        onBookmarkClick = viewModel::updateBookmarkStatus
    )
}
