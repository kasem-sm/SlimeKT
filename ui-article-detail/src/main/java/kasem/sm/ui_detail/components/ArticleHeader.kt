/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.article.domain.model.Article
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.withScale

@Composable
internal fun ArticleHeader(article: Article) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = article.title,
                fontFamily = LocalSlimeFont.current.semiBold,
                lineHeight = 30.sp,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 22.withScale(),
            )
        }
    }
}
