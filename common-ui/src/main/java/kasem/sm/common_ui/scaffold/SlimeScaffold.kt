/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SlimeScaffold(
    state: ScaffoldState,
    bottomBarVisibility: Boolean,
    fabVisibility: Boolean,
    windowSizeClass: WindowSizeClass,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        scaffoldState = state,
        modifier = Modifier,
        content = content,
        bottomBar = {
            if (bottomBarVisibility) {
                if (windowSizeClass.isCompactWindow) {
                    bottomBar()
                }
            }
        },
        floatingActionButton = {
            if (fabVisibility) {
                FloatingActionButton(
                    onClick = {
                        //
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = Icons.Default.Add.name
                    )
                }
            }
        },
    )
}
