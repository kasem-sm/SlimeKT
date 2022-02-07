/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.navigation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kasem.sm.common_ui.util.BottomNavigationItems

@Composable
internal fun SlimeScaffold(
    state: ScaffoldState,
    navController: NavController,
    bottomNavigationItems: List<BottomNavigationItems> = listOf(),
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        scaffoldState = state,
        modifier = Modifier,
        content = content,
        bottomBar = {
            if (navController.isNotAuthRoute()) {
                SlimeBottomBar(
                    navController = navController,
                    items = bottomNavigationItems
                )
            }
        },
        floatingActionButton = {
            if (navController.isNotAuthRoute()) {
                FloatingActionButton(onClick = {
                    //
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = Icons.Default.Add.name
                    )
                }
            }
        }
    )
}
