/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kasem.sm.common_ui.util.Routes

@Composable
internal fun NavController.currentRouteAsState() =
    currentBackStackEntryAsState().value?.destination?.route

@Composable
internal fun NavController.isNotAuthRoute(): Boolean {
    return when (currentRouteAsState()) {
        Routes.HomeScreen.route -> true
        Routes.ExploreScreen().route -> true
        Routes.ProfileScreen.route -> true
        else -> false
    }
}

@Composable
internal fun NavController.isProfileScreenRoute(): Boolean {
    return currentRouteAsState() == Routes.ProfileScreen.route
}
