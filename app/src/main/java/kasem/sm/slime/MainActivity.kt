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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import coil.ImageLoader
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kasem.sm.common_ui.util.Destination
import kasem.sm.dynamic_links_handler.handleDynamicLink
import kasem.sm.slime.ui.navigation.SlimeNavigation
import timber.log.Timber

@OptIn(ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
                windowSizeClass = calculateWindowSizeClass(this)
            )

            handleDynamicLink(
                navigateToDetailScreen = { articleId ->
                    navController.navigate(Destination.articleDetail(articleId))
                },
                onError = {
                    Timber.d("FirebaseDynamicLink Error ${it.message}")
                }
            )
        }
    }
}
