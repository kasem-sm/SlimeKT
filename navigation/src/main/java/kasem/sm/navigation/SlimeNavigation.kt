/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.navigation

import android.os.Build.VERSION_CODES.S
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import kasem.sm.common_ui.util.BottomNavigationItems
import kasem.sm.navigation.components.SlimeScaffold
import kasem.sm.slime.ui.theme.SlimeTheme

@OptIn(ExperimentalMaterialNavigationApi::class)
@RequiresApi(S)
@Composable
fun SlimeNavigation(imageLoader: ImageLoader) = SlimeTheme {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController(bottomSheetNavigator)

    val scaffoldState = rememberScaffoldState()

    SlimeScaffold(
        navController = navController,
        bottomNavigationItems = BottomNavigationItems.toList,
        state = scaffoldState
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
