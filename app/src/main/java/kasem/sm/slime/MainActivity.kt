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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kasem.sm.slime.ui.navigation.SlimeNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @RequiresApi(S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlimeNavigation(imageLoader = imageLoader)
        }
    }
}
