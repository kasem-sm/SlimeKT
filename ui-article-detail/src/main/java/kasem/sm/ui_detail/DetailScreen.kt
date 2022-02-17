/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.ImageLoader
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(DetailState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar
    )

    DetailContent(
        imageLoader = imageLoader,
        state = viewState,
        onRefresh = viewModel::refresh
    )
}
