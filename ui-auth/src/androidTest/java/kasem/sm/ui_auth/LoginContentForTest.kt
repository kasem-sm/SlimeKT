/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_auth.login.LoginContent

@SuppressLint("ComposableNaming")
@Composable
fun loginContentForTest(
    state: AuthState = AuthState(),
    onUsernameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onPasswordToggleClicked: (Boolean) -> Unit = {},
    onSignUpClicked: () -> Unit = {},
) {
    LoginContent(
        state = state,
        onUsernameChanged = onUsernameChanged,
        onPasswordChanged = onPasswordChanged,
        onLoginClicked = onLoginClicked,
        onPasswordToggleClicked = onPasswordToggleClicked,
        onSignUpClicked = onSignUpClicked
    )
}
