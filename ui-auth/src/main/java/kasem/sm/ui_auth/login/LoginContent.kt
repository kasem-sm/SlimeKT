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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_auth.common.LoginButton
import kasem.sm.ui_auth.common.PasswordField
import kasem.sm.ui_auth.common.SignUpButton
import kasem.sm.ui_auth.common.UsernameField

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
                .wrapContentSize()
        ) {
            item {
                Text(
                    text = "Welcome Back",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(vertical = 15.dp),
                    style = getFont(SlimeTypography.SemiBold(24.sp, letterSpacing = 1.sp))
                )
            }

            item {
                UsernameField(
                    text = state.username,
                    onUsernameChanged = onUsernameChanged,
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    onNextClicked = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                )
            }

            item {
                PasswordField(
                    text = state.password,
                    onPasswordChanged = onPasswordChanged,
                    enabled = !state.isLoading,
                    passwordToggle = state.passwordVisibility,
                    onPasswordToggleClick = onPasswordToggleClicked,
                    modifier = Modifier.padding(vertical = 10.dp),
                    onDoneClicked = {
                        onLoginClicked.invoke()
                        keyboardController?.hide()
                    }
                )
            }

            item {
                LoginButton(
                    enabled = !state.isLoading,
                    onContinueClicked = {
                        onLoginClicked.invoke()
                        keyboardController?.hide()
                    },
                    isLoading = state.isLoading,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                )
            }

            item {
                SignUpButton(
                    enabled = !state.isLoading,
                    onSignUpClicked = {
                        onSignUpClicked.invoke()
                    },
                )
            }
        }
    }
}
