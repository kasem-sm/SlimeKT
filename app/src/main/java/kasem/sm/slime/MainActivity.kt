/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime

import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import coil.ImageLoader
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kasem.sm.dynamic_links_handler.handleDynamicLink
import kasem.sm.slime.ui.navigation.BottomNavigatorImpl
import kasem.sm.slime.ui.navigation.SlimeNavigation
import kasem.sm.ui_detail.destinations.DetailScreenDestination
import timber.log.Timber

@OptIn(ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @RequiresApi(S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberAnimatedNavController(bottomSheetNavigator)

            SlimeNavigation(
                navController = navController,
                imageLoader = imageLoader,
                bottomSheetNavigator = bottomSheetNavigator,
                bottomNavigator = BottomNavigatorImpl(navController)
            )

            handleDynamicLink(
                navigateToDetailScreen = { articleId ->
                    navController.navigate(DetailScreenDestination(articleId))
                },
                onError = {
                    Timber.d("FirebaseDynamicLink Error ${it.message}")
                }
            )
        }
    }
}
