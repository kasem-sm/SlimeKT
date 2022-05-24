/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.scaffold

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.util.BottomNavigationItems

@Composable
fun SlimeBottomBar(
    currentRoute: String?,
    navigateTo: (String) -> Unit,
    items: List<BottomNavigationItems>,
) {
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
                        fontFamily = LocalSlimeFont.current.secondaryMedium
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navigateTo(item.route)
                }
            )
        }
    }
}


@Composable
fun SlimeNavigationRail(
    currentRoute: String?,
    navigateTo: (String) -> Unit,
    items: List<BottomNavigationItems>,
) {
    NavigationRail {
        items.forEach { item ->
            NavigationRailItem(
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
                        fontFamily = LocalSlimeFont.current.secondaryMedium
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navigateTo(item.route)
                }
            )
        }
    }
}

val WindowSizeClass.isCompactWindow get() = widthSizeClass == WindowWidthSizeClass.Compact
