/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    snackbarHostState: SnackbarHostState,
    onRegistrationSuccess: () -> Unit
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(AuthState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = onRegistrationSuccess
    )

    RegisterContent(
        state = viewState,
        onUsernameChanged = viewModel::onUsernameChange,
        onPasswordChanged = viewModel::onPasswordChange,
        togglePasswordVisibility = viewModel::togglePasswordVisibility,
        onConfirmClicked = viewModel::registerUser,
        toggleAccountDiscoverability = viewModel::toggleAccountDiscoverability
    )
}
