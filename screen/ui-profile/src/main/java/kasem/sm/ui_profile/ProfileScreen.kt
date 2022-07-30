/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector
import kasem.sm.ui_profile.components.SignInOutButton
import kasem.sm.ui_profile.components.WorkInProgressView

@Destination
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileVM,
    navigator: CommonNavigator
) {
    val isUserAuthenticated by rememberStateWithLifecycle(viewModel.isUserAuthenticated)

    viewModel.uiEvent.safeCollector(
        onSuccessCallback = {
            navigator.navigateEvent(NavigationEvent.NavigateUp)
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        SlimeScreenColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                WorkInProgressView()
            }
            item {
                SignInOutButton(
                    isUserAuthenticated = isUserAuthenticated,
                    clearUserSession = viewModel::clearUserSession,
                    navigateTo = {
                        navigator.navigateEvent(it)
                    }
                )
            }
        }
    }
}
