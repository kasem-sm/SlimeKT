/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.VerticalSpacer
import kasem.sm.common_ui.getFont
import kasem.sm.ui_subscribe_category.R

@Composable
internal fun ContentHeader() {
    Column {
        Text(
            text = stringResource(R.string.header_title),
            color = MaterialTheme.colorScheme.primary,
            style = getFont(SlimeTypography.SemiBold(24.sp, letterSpacing = 1.sp)),
        )
        VerticalSpacer(value = 10.dp)
        Text(
            text = stringResource(R.string.header_description),
            color = MaterialTheme.colorScheme.primary,
            style = getFont(SlimeTypography.SecondaryRegular(16.sp)),
        )
    }
}
