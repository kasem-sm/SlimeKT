/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

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
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_auth.common.BottomSheetHandle
import kasem.sm.ui_auth.common.PasswordField
import kasem.sm.ui_auth.common.UsernameField
import kasem.sm.ui_auth.register.components.ConfirmRegistrationButton
import kasem.sm.ui_auth.register.components.DiscoverableToOtherUsersToggle
import kasem.sm.ui_auth.register.components.Header

/**
 * This composable maintains the entire screen for handling user registration.
 *
 * @param[state] The current state of the screen to render.
 * @param[onUsernameChanged] A callback invoked when the user enters their username.
 * @param[onPasswordChanged] A callback invoked when the user enters their password.
 * @param[onConfirmClicked] A callback invoked when the user clicks the register button.
 */

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
                .wrapContentSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                BottomSheetHandle()
            }

            item {
                Header("Get Started", "Publish Your Passion")
            }

            item {
                UsernameField(
                    state = state,
                    modifier = Modifier.padding(vertical = 5.dp),
                    onUsernameChanged = onUsernameChanged,
                    onNextClicked = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            }

            item {
                PasswordField(
                    state = state,
                    modifier = Modifier.padding(vertical = 5.dp),
                    onPasswordChanged = onPasswordChanged,
                    onPasswordToggleClick = togglePasswordVisibility,
                    onDoneClicked = {
                        keyboardController?.hide()
                        onConfirmClicked()
                    }
                )
            }

            item {
                DiscoverableToOtherUsersToggle(
                    state = state,
                    toggleAccountDiscoverability = toggleAccountDiscoverability
                )
            }

            item {
                ConfirmRegistrationButton(
                    state = state,
                    onClick = {
                        keyboardController?.hide()
                        onConfirmClicked()
                    },
                )
            }
        }
    }
}
