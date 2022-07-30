/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.destination
import kasem.sm.ui_bookmarks.destinations.BookmarksScreenDestination
import kasem.sm.ui_explore.destinations.ExploreScreenDestination
import kasem.sm.ui_home.destinations.HomeScreenDestination
import kasem.sm.ui_profile.destinations.ProfileScreenDestination

@Composable
fun NavController.currentDestinationAsState(): DestinationSpec<*>? {
    return currentBackStackEntryAsState().value?.destination()
}

@Composable
fun NavController.isNotAuthRoute(): Boolean {
    return when (currentDestinationAsState()) {
        HomeScreenDestination -> true
        ExploreScreenDestination -> true
        ProfileScreenDestination -> true
        BookmarksScreenDestination -> true
        else -> false
    }
}

@Composable
fun NavController.isProfileScreenRoute(): Boolean {
    return currentDestinationAsState() == ProfileScreenDestination
}
