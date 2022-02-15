/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont
import kasem.sm.common_ui.util.BottomNavigationItems

@Composable
internal fun SlimeBottomBar(
    navController: NavController,
    items: List<BottomNavigationItems>,
) {

    Column {
        val currentRoute = navController.currentRouteAsState()

        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            modifier = Modifier.size(22.dp),
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = getFont(SlimeTypography.SecondaryMedium())
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (navController.currentDestination?.route != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }
}
