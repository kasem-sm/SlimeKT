/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont
import kasem.sm.feature_article.domain.model.Article

@Composable
internal fun ArticleHeader(article: Article) {
    Text(
        text = article.title,
        style = getFont(SlimeTypography.Bold(letterSpacing = 1.sp))
            .copy(lineHeight = 32.sp),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
    )
}
