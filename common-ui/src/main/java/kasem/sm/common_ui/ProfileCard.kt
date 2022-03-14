/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import kasem.sm.common_ui.util.clickWithRipple

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    username: String,
    bio: String,
) {
    SlimeCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .clickWithRipple { },
        elevation = 1.dp,
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            SlimeGradientProfile(
                username = username
            )
            Column(
                Modifier.weight(0.5f)
            ) {
                Text(
                    text = username,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    letterSpacing = 1.5.sp,
                    fontFamily = LocalSlimeFont.current.semiBold
                )

                VerticalSpacer(value = 10.dp)

                Text(
                    text = bio,
                    fontFamily = LocalSlimeFont.current.secondaryRegular,
                    fontSize = 14.sp,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

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
