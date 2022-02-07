/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kasem.sm.common_ui.util.BottomNavigationItems
import kasem.sm.slime.navigation.Navigation
import kasem.sm.slime.navigation.components.SlimeScaffold
import kasem.sm.slime.ui.theme.SlimeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlimeTheme {
                val navController = rememberAnimatedNavController()

                val scaffoldState = rememberScaffoldState()

                SlimeScaffold(
                    navController = navController,
                    bottomNavigationItems = BottomNavigationItems.toList,
                    state = scaffoldState
                ) { padding ->
                    Navigation(
                        navController = navController,
                        imageLoader = imageLoader,
                        snackbarHostState = scaffoldState.snackbarHostState,
                        modifier = Modifier
                            .padding(padding)
                    )
                }
            }
        }
    }
}
