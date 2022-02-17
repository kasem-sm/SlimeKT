/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.common_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.SlimeElevatedButton

@Composable
fun BoxScope.emptyArticleView(onContributeClick: () -> Unit) = apply {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "\uD83D\uDE25",
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
        Text(
            text = "Nothing found!",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
        SlimeElevatedButton(text = "Contribute", onClick = onContributeClick)
    }
}
