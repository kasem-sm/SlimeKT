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
) {
    object Home : BottomNavigationItems(
        "Home",
        R.drawable.ic_home,
    )

    object Explore :
        BottomNavigationItems(
            "Explore",
            R.drawable.ic_discover,
        )

    object Bookmarks : BottomNavigationItems(
        "Bookmarks",
        R.drawable.ic_bookmark,
    )

    object Profile : BottomNavigationItems(
        "Profile",
        R.drawable.ic_profile,
    )

    companion object {
        val toList = listOf(Home, Explore, Bookmarks, Profile)
    }
}
