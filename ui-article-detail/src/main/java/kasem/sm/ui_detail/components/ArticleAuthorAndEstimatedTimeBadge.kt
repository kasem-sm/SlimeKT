/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.article.common_ui.SlimeGradientProfile
import kasem.sm.article.common_ui.toDate
import kasem.sm.article.common_ui.toEstimatedTime
import kasem.sm.article.common_ui.withAuthorAndPostedTime
import kasem.sm.article.domain.model.Article
import kasem.sm.common_ui.LocalSlimeFont

@Composable
internal fun ArticleAuthorAndEstimatedTimeBadge(
    article: Article,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        SlimeGradientProfile(
            size = 35.dp,
            username = article.author
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row {
                Text(
                    text = withAuthorAndPostedTime(
                        article.author,
                        article.timestamp.toDate()
                    ),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            }
            Row {
                Text(
                    text = "Est time to read: ${article.description.length.toEstimatedTime()} min",
                    maxLines = 1,
                    fontFamily = LocalSlimeFont.current.secondaryMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            }
        }
    }
}