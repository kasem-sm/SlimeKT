/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.VerticalSpacer
import kasem.sm.common_ui.getFont
import kasem.sm.ui_login.components.LoginButton
import kasem.sm.ui_login.components.PasswordField
import kasem.sm.ui_login.components.SignUpButton
import kasem.sm.ui_login.components.UsernameField

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
    modifier: Modifier = Modifier,
    viewState: LoginViewState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onPasswordToggleClicked: (Boolean) -> Unit,
    onSignUpClicked: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        SlimeScreenColumn(
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            item {
                Text(
                    text = "Welcome Back",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(10.dp),
                    style = getFont(SlimeTypography.SemiBold(24.sp, letterSpacing = 1.sp))
                )
            }

            item {
                UsernameField(
                    text = viewState.username,
                    onUsernameChanged = onUsernameChanged,
                    enabled = !viewState.isLoading,
                )
            }

            item {
                PasswordField(
                    text = viewState.password,
                    onPasswordChanged = onPasswordChanged,
                    enabled = !viewState.isLoading,
                    passwordToggle = viewState.passwordVisibility,
                    onPasswordToggleClick = onPasswordToggleClicked
                )
            }

            item {
                LoginButton(
                    enabled = !viewState.isLoading,
                    onContinueClicked = {
                        onLoginClicked.invoke()
                        keyboardController?.hide()
                    },
                    isLoading = viewState.isLoading
                )
            }

            item {
                VerticalSpacer(value = 20.dp)
                SignUpButton(
                    enabled = !viewState.isLoading,
                    onSignUpClicked = {
                        onSignUpClicked.invoke()
                    }
                )
            }
        }
    }
}
