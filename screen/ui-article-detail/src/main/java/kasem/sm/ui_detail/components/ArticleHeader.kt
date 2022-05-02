/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.withScale

@Composable
internal fun ArticleHeader(articleTitle: String, onShareClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = articleTitle,
            fontFamily = LocalSlimeFont.current.semiBold,
            lineHeight = 30.sp,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 22.withScale(),
            modifier = Modifier
                .weight(0.9f)
        )

        val icon = Icons.Default.Share
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name,
                modifier = Modifier
                    .weight(0.1f),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
