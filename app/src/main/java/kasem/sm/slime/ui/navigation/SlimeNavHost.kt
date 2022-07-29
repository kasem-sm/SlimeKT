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
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import kasem.sm.common_ui.InitSlimeSystemUI

@ExperimentalMaterialNavigationApi
@Composable
fun SlimeNavHost(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    InitSlimeSystemUI()
    ModalBottomSheetLayout(bottomSheetNavigator) {
        AppNavigation(
            navController = navController,
            imageLoader = imageLoader,
            snackbarHostState = snackbarHostState,
            modifier = modifier
        )
    }
}
