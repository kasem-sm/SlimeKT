/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.common_ui.VerticalSpacer

@Composable
internal fun ContentHeader(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.subscribe_topic_header),
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.5.sp,
            fontSize = 24.sp,
            fontFamily = LocalSlimeFont.current.semiBold
        )
        VerticalSpacer(value = 15.dp)
        Text(
            text = stringResource(R.string.subscribe_topic_header_desc),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
    }
}
