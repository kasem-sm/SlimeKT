/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.common_ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A customizable circular progress indicator for Slime.
 */

@Composable
fun BoxScope.SlimeProgressIndicator(
    strokeWidth: Dp = 4.dp
) {
    CircularProgressIndicator(
        modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Center),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = strokeWidth
    )
}
