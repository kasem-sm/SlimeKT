/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * A composable to create a Vertical Space between
 * existing composable.
 *
 * @param[value] the value of the space in [Dp]
 */

@Composable
fun VerticalSpacer(value: Dp) {
    Spacer(modifier = Modifier.height(value))
}

/**
 * A composable to create a Horizontal Space between
 * existing composable.
 *
 * @param[value] the value of the space in [Dp]
 */

@Composable
fun HorizontalSpacer(value: Dp) {
    Spacer(modifier = Modifier.width(value))
}
