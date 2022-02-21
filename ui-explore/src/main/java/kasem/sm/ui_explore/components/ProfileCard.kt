/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.article.common_ui.SlimeGradientProfile
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.VerticalSpacer
import kasem.sm.common_ui.util.clickWithRipple

@Composable
internal fun ProfileCard(
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