/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.common_ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont

@Composable
fun TopicChip(
    modifier: Modifier = Modifier,
    topic: String,
    chipBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    chipTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = chipBackgroundColor
    ) {
        Text(
            text = topic,
            color = chipTextColor,
            modifier = Modifier.padding(10.dp),
            fontSize = 14.sp,
            fontFamily = LocalSlimeFont.current.semiBold,
        )
    }
}
