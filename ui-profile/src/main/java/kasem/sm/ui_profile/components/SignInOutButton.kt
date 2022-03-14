/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kasem.sm.common_ui.SlimeElevatedButton
import kasem.sm.common_ui.util.Routes

@Composable
internal fun SignInOutButton(
    isUserAuthenticated: Boolean,
    clearUserSession: () -> Unit,
    navigateTo: (String) -> Unit
) {
    SlimeElevatedButton(
        text = if (isUserAuthenticated) "Log Out" else "Log In",
        onClick = {
            if (isUserAuthenticated) {
                clearUserSession()
            } else navigateTo(Routes.LoginScreen.route)
        },
        backgroundColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary,
    )
}
