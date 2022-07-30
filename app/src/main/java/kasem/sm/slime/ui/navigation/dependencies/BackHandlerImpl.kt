/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.ui.navigation.dependencies

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import kasem.sm.ui_core.BackHandler

class BackHandlerImpl : BackHandler {
    @Composable
    override fun invoke(enabled: Boolean, onBack: () -> Unit) {
        BackHandler(enabled, onBack)
    }
}
