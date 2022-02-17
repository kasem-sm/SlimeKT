/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont

val listOfRandomColor = listOf(
    Color(0xFF2B2BD6),
    Color(0xFF5F0D6D),
    Color(0xFFE02D81),
    Color(0xFF08297C),
    Color(0xFF7C0829),
    Color(0xFFEC0909),
)

@Composable
fun SlimeGradientProfile(
    modifier: Modifier = Modifier,
    username: String,
    size: Dp = 40.dp,
    textSize: TextUnit = 20.sp,
    color: Color = listOfRandomColor.random(),
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Brush.linearGradient(listOf(color, color.copy(0.5f))))
    ) {
        Text(
            text = username.first().toString(),
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = textSize,
            color = Color.White,
            fontFamily = LocalSlimeFont.current.semiBold
        )
    }
}
