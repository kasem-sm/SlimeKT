/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class FocusedState(
    val interactionSource: MutableInteractionSource,
    val focused: Boolean
)

@Composable
fun rememberFocusedState(): FocusedState {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    return FocusedState(
        interactionSource = interactionSource,
        focused = interactionSource.collectIsFocusedAsState().value
    )
}
