/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

import androidx.compose.runtime.Composable

interface BackHandler {
    @Composable
    operator fun invoke(enabled: Boolean, onBack: () -> Unit)
}
