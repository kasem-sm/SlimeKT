/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun LoginScreen(
    viewModel: LoginVM,
    snackbarHostState: SnackbarHostState,
    navigator: CommonNavigator
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = {
            navigator.navigateEvent(NavigationEvent.NavigateUp)
        }
    )

    LoginContent(
        state = viewState,
        onUsernameChanged = viewModel::onUsernameChange,
        onPasswordChanged = viewModel::onPasswordChange,
        onLoginClicked = viewModel::loginUser,
        onSignUpClicked = {
            navigator.navigateEvent(NavigationEvent.NavigateUp)
            navigator.navigateEvent(NavigationEvent.Register)
        },
        onPasswordToggleClicked = viewModel::togglePasswordVisibility
    )
}
