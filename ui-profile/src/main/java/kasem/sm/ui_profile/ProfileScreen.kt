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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.SlimeElevatedButton
import kasem.sm.ui_core.safeCollector

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    navigateTo: (String) -> Unit
) {
    viewModel.uiEvent.safeCollector(
        onRouteReceived = navigateTo
    )

    LaunchedEffect(key1 = true) {
        viewModel.verifyAuthenticationStatus()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Content
        SlimeElevatedButton(
            text = "Log Out",
            onClick = {
                viewModel.clearUserSession()
            }
        )
    }
}
