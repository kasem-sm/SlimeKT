/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.SlimeElevatedButton
import kasem.sm.common_ui.util.Routes
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileVM,
    onLogOutSuccess: () -> Unit,
    navigateTo: (String) -> Unit
) {
    val isUserAuthenticated =
        rememberFlow(viewModel.isUserAuthenticated).collectAsState(initial = false).value

    viewModel.uiEvent.safeCollector(
        onSuccessCallback = onLogOutSuccess
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Content

        SlimeElevatedButton(
            text = if (isUserAuthenticated) "Log Out" else "Log In",
            onClick = {
                if (isUserAuthenticated) {
                    viewModel.clearUserSession()
                } else navigateTo(Routes.LoginScreen.route)
            }
        )
    }
}
