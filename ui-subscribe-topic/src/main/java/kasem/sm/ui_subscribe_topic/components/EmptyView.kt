/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont

@Composable
internal fun EmptyView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "😎",
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
        Text(
            text = "Damn, Slimer!\nYou have subscribed to every topic.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
    }
}
