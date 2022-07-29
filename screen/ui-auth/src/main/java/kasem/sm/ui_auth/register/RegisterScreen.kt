/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

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
fun RegisterScreen(
    viewModel: RegisterVM,
    snackbarHostState: SnackbarHostState,
    navigator: CommonNavigator
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = {
            navigator.navigateEvent(NavigationEvent.NavigateUp)        }
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
