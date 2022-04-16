/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    message: String = "Nothing found!",
    emoji: String = "\uD83D\uDE25",
    onContributeClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = emoji,
            textAlign = TextAlign.Center,
            fontSize = 50.withScale(),
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.withScale(),
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
        SlimeElevatedButton(text = "Contribute", onClick = onContributeClick)
    }
}
