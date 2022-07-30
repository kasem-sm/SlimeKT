/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation

import android.os.Build.VERSION_CODES.S
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import kasem.sm.common_ui.scaffold.SlimeBottomBar
import kasem.sm.common_ui.scaffold.SlimeScaffold
import kasem.sm.common_ui.theme.SlimeTheme
import kasem.sm.common_ui.util.BottomNavigationItems
import kasem.sm.common_ui.util.BottomNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@RequiresApi(S)
@Composable
fun SlimeNavigation(
    imageLoader: ImageLoader,
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    bottomNavigator: BottomNavigator
) = SlimeTheme {
    val scaffoldState = rememberScaffoldState()

    SlimeScaffold(
        state = scaffoldState,
        bottomBarVisibility = navController.isNotAuthRoute(),
        fabVisibility = navController.isProfileScreenRoute(),
        bottomBar = {
            SlimeBottomBar(
                items = BottomNavigationItems.toList,
                bottomNavigator = bottomNavigator
            )
        }
    ) { padding ->
        Surface {
            SlimeNavHost(
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
