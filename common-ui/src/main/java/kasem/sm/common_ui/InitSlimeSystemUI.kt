/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun InitSlimeSystemUI() {
    val color = MaterialTheme.colorScheme.surface
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setStatusBarColor(color, !isDarkTheme)
    }
}
