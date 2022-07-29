/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.util

import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import kasem.sm.common_ui.util.BottomNavigationItems
import kasem.sm.ui_bookmarks.destinations.BookmarksScreenDestination
import kasem.sm.ui_explore.destinations.ExploreScreenDestination
import kasem.sm.ui_home.destinations.HomeScreenDestination
import kasem.sm.ui_profile.destinations.ProfileScreenDestination

fun BottomNavigationItems.toDirectionDestination(): DirectionDestinationSpec {
    return when (this) {
        BottomNavigationItems.Bookmarks -> BookmarksScreenDestination
        BottomNavigationItems.Explore -> ExploreScreenDestination
        BottomNavigationItems.Home -> HomeScreenDestination
        BottomNavigationItems.Profile -> ProfileScreenDestination
    }
}
