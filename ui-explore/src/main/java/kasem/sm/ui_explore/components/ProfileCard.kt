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
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.VerticalSpacer
import kasem.sm.common_ui.getFont
import kasem.sm.common_ui.util.clickWithRipple
import kasem.sm.feature_article.common_ui.SlimeGradientProfile

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
                    style = getFont(
                        typography = SlimeTypography.SemiBold(
                            letterSpacing = 1.5.sp
                        )
                    ),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                VerticalSpacer(value = 10.dp)

                Text(
                    text = bio,
                    style = getFont(
                        typography = SlimeTypography.SecondaryRegular()
                    ),
                    fontSize = 14.sp,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
