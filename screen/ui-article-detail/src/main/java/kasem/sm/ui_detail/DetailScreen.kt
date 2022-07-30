/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil.ImageLoader
import com.ramcosta.composedestinations.annotation.Destination
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

data class DetailScreenArgs(
    val id: Int
)

@Destination(
    navArgsDelegate = DetailScreenArgs::class
)
@Composable
fun DetailScreen(
    viewModel: DetailVM,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState
) {
    val state by rememberStateWithLifecycle(viewModel.state)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar
    )

    DetailContent(
        imageLoader = imageLoader,
        state = state,
        snackbarHostState = snackbarHostState,
        onRefresh = viewModel::refresh,
    )
}
