/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

sealed class NavigationEvent {
    object Login : NavigationEvent()
    object Register : NavigationEvent()
    object Explore : NavigationEvent()
    object Bookmarks : NavigationEvent()
    object Profile : NavigationEvent()

    data class ListScreen(val title: String, val id: String) : NavigationEvent()
    data class Detail(val id: Int) : NavigationEvent()
    object Subscription : NavigationEvent()

    object NavigateUp : NavigationEvent()
}
