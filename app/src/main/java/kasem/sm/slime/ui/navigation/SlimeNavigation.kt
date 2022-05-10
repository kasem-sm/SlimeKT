/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation

import android.os.Build.VERSION_CODES.S
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import kasem.sm.common_ui.scaffold.SlimeBottomBar
import kasem.sm.common_ui.scaffold.SlimeScaffold
import kasem.sm.common_ui.theme.SlimeTheme
import kasem.sm.common_ui.util.BottomNavigationItems

@OptIn(ExperimentalMaterialNavigationApi::class)
@RequiresApi(S)
@Composable
fun SlimeNavigation(
    imageLoader: ImageLoader,
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
) = SlimeTheme {
    val scaffoldState = rememberScaffoldState()

    SlimeScaffold(
        state = scaffoldState,
        bottomBarVisibility = navController.isNotAuthRoute(),
        fabVisibility = navController.isProfileScreenRoute(),
        bottomBar = {
            SlimeBottomBar(
                currentRoute = navController.currentRouteAsState(),
                items = BottomNavigationItems.toList,
                navigateTo = { route ->
                    if (navController.currentDestination?.route != route) {
                        navController.navigate(route) {
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
    ) { padding ->
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
