/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationFlow
import kasem.sm.common_ui.util.BottomNavigationItems
import kasem.sm.common_ui.util.BottomNavigator
import kasem.sm.slime.ui.util.toDirectionDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BottomNavigatorImpl(private val navController: NavController) : BottomNavigator {
    override fun navigate(item: BottomNavigationItems) {
        if (navController.currentDestination?.route !== item.toDirectionDestination().route) {
            navController.navigate(item.toDirectionDestination()) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    override fun isBottomNavItemSelectedAsFlow(item: BottomNavigationItems): Flow<Boolean> {
        return navController.currentDestinationFlow.map { it === item.toDirectionDestination() }
    }
}
