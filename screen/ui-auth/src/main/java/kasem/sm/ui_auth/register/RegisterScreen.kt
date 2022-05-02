/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@Composable
fun RegisterScreen(
    viewModel: RegisterVM,
    snackbarHostState: SnackbarHostState,
    onRegistrationSuccess: () -> Unit
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

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
