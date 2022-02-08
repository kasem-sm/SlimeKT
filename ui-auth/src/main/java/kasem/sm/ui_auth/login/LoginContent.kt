/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont
import kasem.sm.ui_auth.common.AuthViewState
import kasem.sm.ui_auth.common.LoginButton
import kasem.sm.ui_auth.common.PasswordField
import kasem.sm.ui_auth.common.SignUpButton
import kasem.sm.ui_auth.common.UsernameField

/**
 * This composable maintains the entire screen for handling user login.
 *
 * @param[viewState] The current state of the screen to render.
 * @param[onUsernameChanged] A callback invoked when the user enters their username.
 * @param[onPasswordChanged] A callback invoked when the user enters their password.
 * @param[onLoginClicked] A callback invoked when the user clicks the login button.
 * @param[onSignUpClicked] A callback invoked when the user taps the Sign In button.
 */

@Composable
internal fun LoginContent(
    viewState: AuthViewState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onPasswordToggleClicked: (Boolean) -> Unit,
    onSignUpClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        SlimeScreenColumn(
            verticalArrangement = Arrangement.Bottom,
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
                    text = viewState.username,
                    onUsernameChanged = onUsernameChanged,
                    enabled = !viewState.isLoading,
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                )
            }

            item {
                PasswordField(
                    text = viewState.password,
                    onPasswordChanged = onPasswordChanged,
                    enabled = !viewState.isLoading,
                    passwordToggle = viewState.passwordVisibility,
                    onPasswordToggleClick = onPasswordToggleClicked,
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                )
            }

            item {
                LoginButton(
                    enabled = !viewState.isLoading,
                    onContinueClicked = {
                        onLoginClicked.invoke()
                        keyboardController?.hide()
                    },
                    isLoading = viewState.isLoading,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                )
            }

            item {
                SignUpButton(
                    enabled = !viewState.isLoading,
                    onSignUpClicked = {
                        onSignUpClicked.invoke()
                    },
                )
            }
        }
    }
}
