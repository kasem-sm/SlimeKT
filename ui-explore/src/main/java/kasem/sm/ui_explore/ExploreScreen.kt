/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector
import kotlinx.coroutines.FlowPreview

@FlowPreview
@Composable
fun ExploreScreen(
    viewModel: ExploreVM,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onCategoryClick: (title: String, id: String) -> Unit,
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(ExploreState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar
    )

    ExploreContent(
        state = viewState,
        onRefresh = viewModel::refresh,
        imageLoader = imageLoader,
        onArticleClick = onArticleClick,
        onCategoryClick = onCategoryClick,
    )
}
