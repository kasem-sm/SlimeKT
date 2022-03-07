/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@Composable
fun LoginScreen(
    viewModel: LoginVM,
    onLoginSuccess: () -> Unit,
    onSignUpClicked: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = onLoginSuccess
    )

    LoginContent(
        state = viewState,
        onUsernameChanged = viewModel::onUsernameChange,
        onPasswordChanged = viewModel::onPasswordChange,
        onLoginClicked = viewModel::loginUser,
        onSignUpClicked = onSignUpClicked,
        onPasswordToggleClicked = viewModel::togglePasswordVisibility
    )
}
