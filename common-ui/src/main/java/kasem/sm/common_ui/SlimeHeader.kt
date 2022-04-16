/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun SlimeHeader(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    fontFamily: FontFamily = LocalSlimeFont.current.semiBold,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier
            .fillMaxWidth(),
        fontFamily = fontFamily,
        fontSize = 22.withScale(),
        lineHeight = 35.sp
    )
}
