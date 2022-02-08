/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import kasem.sm.ui_auth.common.AuthViewState
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit,
    onSignUpClicked: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(AuthViewState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onRouteReceived = { route ->
            onLoginSuccess(route)
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.checkAuthenticationStatus()
    }

    LoginContent(
        viewState = viewState,
        onUsernameChanged = viewModel::onUsernameChange,
        onPasswordChanged = viewModel::onPasswordChange,
        onLoginClicked = viewModel::loginUser,
        onSignUpClicked = onSignUpClicked,
        onPasswordToggleClicked = viewModel::togglePasswordVisibility
    )
}
