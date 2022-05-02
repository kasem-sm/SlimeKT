/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kasem.sm.authentication.domain.model.AuthState
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.ui_auth.components.BottomSheetHandle
import kasem.sm.ui_auth.components.Header
import kasem.sm.ui_auth.components.LoginButton
import kasem.sm.ui_auth.components.PasswordField
import kasem.sm.ui_auth.components.SignUpButton
import kasem.sm.ui_auth.components.UsernameField

/**
 * This composable maintains the entire screen for handling user login.
 *
 * @param[state] The current state of the screen to render.
 * @param[onUsernameChanged] A callback invoked when the user enters their username.
 * @param[onPasswordChanged] A callback invoked when the user enters their password.
 * @param[onLoginClicked] A callback invoked when the user clicks the login button.
 * @param[onSignUpClicked] A callback invoked when the user taps the Sign In button.
 */

@Composable
internal fun LoginContent(
    state: AuthState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onPasswordToggleClicked: (Boolean) -> Unit,
    onSignUpClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        SlimeScreenColumn(
            modifier = Modifier
                .wrapContentSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                BottomSheetHandle()
            }
            item {
                Header("Login", "Welcome back")
            }
            item {
                UsernameField(
                    state = state,
                    onUsernameChanged = onUsernameChanged,
                    modifier = Modifier.padding(vertical = 5.dp),
                    onNextClicked = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                )
            }

            item {
                PasswordField(
                    modifier = Modifier.padding(vertical = 5.dp),
                    state = state,
                    onPasswordChanged = onPasswordChanged,
                    onPasswordToggleClick = onPasswordToggleClicked,
                    onDoneClicked = {
                        onLoginClicked.invoke()
                        keyboardController?.hide()
                    }
                )
            }

            item {
                LoginButton(
                    onContinueClicked = {
                        onLoginClicked.invoke()
                        keyboardController?.hide()
                    },
                    isLoading = state.isLoading
                )
            }

            item {
                SignUpButton(
                    enabled = !state.isLoading,
                    onSignUpClicked = onSignUpClicked,
                )
            }
        }
    }
}
