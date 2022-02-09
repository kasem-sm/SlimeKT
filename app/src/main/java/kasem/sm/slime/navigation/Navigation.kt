/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kasem.sm.common_ui.util.Routes.MainRoute
import kasem.sm.common_ui.util.Routes.ProfileScreen

@Composable
internal fun Navigation(
    navController: NavHostController,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    InitSlimeSystemUI()
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ProfileScreen.route,
        route = MainRoute.route,
    ) {
        attachLoginScreen(snackbarHostState, navController)
        attachRegistrationScreen(snackbarHostState, navController)
        attachHomeScreen(imageLoader, navController, snackbarHostState)
        attachExploreScreen(navController, imageLoader, snackbarHostState)
        attachProfileScreen(navController)
        attachArticleDetailScreen(imageLoader, snackbarHostState)
        attachSelectTopicsScreen(navController, snackbarHostState)
        attachListScreen(imageLoader, snackbarHostState, navController)
    }
}

@Composable
private fun InitSlimeSystemUI() {
    val color = MaterialTheme.colorScheme.surface
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setStatusBarColor(color, !isDarkTheme)
    }
}
