/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_register

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@ExperimentalComposeUiApi
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegistrationSuccess: (String) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(RegisterViewState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onRouteReceived = onRegistrationSuccess
    )

    RegisterContent(
        viewState = viewState,
        onUsernameChanged = viewModel::onUsernameChange,
        onPasswordChanged = viewModel::onPasswordChange,
        togglePasswordVisibility = viewModel::togglePasswordVisibility,
        onConfirmClicked = viewModel::registerUser,
        toggleAccountDiscoverability = viewModel::toggleAccountDiscoverability
    )
}
