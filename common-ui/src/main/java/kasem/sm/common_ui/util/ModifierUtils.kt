/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickWithRipple(
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed {
    then(
        clickable(
            enabled = enabled,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false),
            onClick = onClick
        )
    )
}

fun Modifier.toggleWithRipple(value: Boolean, onClick: (Boolean) -> Unit) = composed {
    then(
        this.toggleable(
            value = value,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false),
            onValueChange = onClick
        )
    )
}
