/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation

import android.os.Build.VERSION_CODES.S
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import kasem.sm.common_ui.scaffold.SlimeBottomBar
import kasem.sm.common_ui.scaffold.SlimeNavigationRail
import kasem.sm.common_ui.scaffold.SlimeScaffold
import kasem.sm.common_ui.scaffold.isCompactWindow
import kasem.sm.common_ui.theme.SlimeTheme
import kasem.sm.common_ui.util.BottomNavigationItems

@OptIn(ExperimentalMaterialNavigationApi::class)
@RequiresApi(S)
@Composable
fun SlimeNavigation(
    imageLoader: ImageLoader,
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    windowSizeClass: WindowSizeClass,
) {
    SlimeTheme {
        val scaffoldState = rememberScaffoldState()

        SlimeScaffold(
            state = scaffoldState,
            bottomBarVisibility = navController.isNotAuthRoute(),
            fabVisibility = navController.isProfileScreenRoute(),
            windowSizeClass = windowSizeClass,
            bottomBar = {
                SlimeBottomBar(
                    currentRoute = navController.currentRouteAsState(),
                    items = BottomNavigationItems.toList,
                    navigateTo = navController::navigateTo
                )
            },
        ) { padding ->
            /**
             * If the device is rotated (horizontally), we need to display
             * the rail first in the row then the content
             */
            Row(
                Modifier
                    .fillMaxSize()
            ) {
                if (!windowSizeClass.isCompactWindow) {
                    SlimeNavigationRail(
                        currentRoute = navController.currentRouteAsState(),
                        items = BottomNavigationItems.toList,
                        navigateTo = navController::navigateTo
                    )
                }

                NavHost(
                    navController = navController,
                    bottomSheetNavigator = bottomSheetNavigator,
                    imageLoader = imageLoader,
                    snackbarHostState = scaffoldState.snackbarHostState,
                    modifier = Modifier
                        .padding(padding)
                )
            }
        }
    }
}

fun NavController.navigateTo(route: String) {
    if (currentDestination?.route != route) {
        navigate(route) {
            popUpTo(graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
