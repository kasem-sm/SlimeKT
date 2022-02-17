/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.feature_article.domain.model.Article

@Composable
internal fun ArticleHeader(article: Article) {
    Text(
        text = article.title,
        fontFamily = LocalSlimeFont.current.semiBold,
        lineHeight = 30.sp,
        letterSpacing = 1.sp,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 22.sp,
    )
}
