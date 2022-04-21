/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import kasem.sm.common_ui.InitSlimeSystemUI
import kasem.sm.common_ui.util.Routes
import kasem.sm.common_ui.util.Routes.Main

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavHost(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    InitSlimeSystemUI()
    ModalBottomSheetLayout(bottomSheetNavigator) {
        AnimatedNavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Routes.HomeScreen.route,
            route = Main.route,
        ) {
            attachLoginScreen(navController, snackbarHostState)
            attachRegistrationScreen(navController, snackbarHostState)
            attachHomeScreen(imageLoader, navController, snackbarHostState)
            attachExploreScreen(navController, imageLoader, snackbarHostState)
            attachProfileScreen(navController)
            attachArticleDetailScreen(imageLoader, snackbarHostState)
            attachSelectTopicsScreen(navController, snackbarHostState)
            attachListScreen(imageLoader, snackbarHostState, navController)
            attachBookmarksScreen(imageLoader, navController)
        }
    }
}
