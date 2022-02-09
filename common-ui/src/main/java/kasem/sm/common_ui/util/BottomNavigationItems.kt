/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import androidx.annotation.DrawableRes
import kasem.sm.common_ui.R

sealed class BottomNavigationItems(
    val title: String,
    @DrawableRes val icon: Int,
    val route: String,
) {

    object Home : BottomNavigationItems(
        "Home",
        R.drawable.ic_home,
        Routes.HomeScreen.route
    )

    object Explore :
        BottomNavigationItems(
            "Explore",
            R.drawable.ic_discover,
            Routes.ExploreScreen().route
        )

    object Profile : BottomNavigationItems(
        "Profile",
        R.drawable.ic_profile,
        Routes.ProfileScreen.route
    )

    companion object {
        val toList = listOf(Home, Explore, Profile)
    }
}
