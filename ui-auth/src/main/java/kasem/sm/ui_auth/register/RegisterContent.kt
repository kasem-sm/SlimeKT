/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_auth.common.PasswordField
import kasem.sm.ui_auth.common.UsernameField

/**
 * This composable maintains the entire screen for handling user registration.
 *
 * @param[state] The current state of the screen to render.
 * @param[onUsernameChanged] A callback invoked when the user enters their username.
 * @param[onPasswordChanged] A callback invoked when the user enters their password.
 * @param[onConfirmClicked] A callback invoked when the user clicks the register button.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegisterContent(
    state: AuthState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmClicked: () -> Unit,
    togglePasswordVisibility: (Boolean) -> Unit,
    toggleAccountDiscoverability: (Boolean) -> Unit,
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
                    text = stringResource(id = R.string.publish_your_passion),
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp,
                    fontSize = 24.sp,
                    fontFamily = LocalSlimeFont.current.semiBold
                )
                Text(
                    text = stringResource(id = R.string.get_started),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(vertical = 15.dp),
                    fontSize = 16.sp,
                    fontFamily = LocalSlimeFont.current.medium
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
                    }
                )
            }

            item {
                PasswordField(
                    text = state.password,
                    onPasswordChanged = onPasswordChanged,
                    enabled = !state.isLoading,
                    passwordToggle = state.passwordVisibility,
                    onPasswordToggleClick = togglePasswordVisibility,
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    onDoneClicked = {
                        keyboardController?.hide()
                        onConfirmClicked()
                    }
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Checkbox(
                        checked = state.isAccountDiscoverable,
                        onCheckedChange = toggleAccountDiscoverability
                    )
                    Text(
                        text = stringResource(id = R.string.auth_usr_msg),
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 1.sp,
                        fontSize = 14.sp,
                        fontFamily = LocalSlimeFont.current.medium
                    )
                }
            }

            item {
                SlimePrimaryButton(
                    text = stringResource(id = R.string.confirm_reg),
                    onClick = {
                        keyboardController?.hide()
                        onConfirmClicked()
                    },
                    enabled = !state.isLoading,
                    isLoading = state.isLoading,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                )
            }
        }
    }
}