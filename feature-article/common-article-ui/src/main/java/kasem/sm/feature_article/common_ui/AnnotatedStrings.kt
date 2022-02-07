/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.common_ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont

@Composable
internal fun titleWithDot(
    title: String,
    alreadyRead: Boolean
) = buildAnnotatedString {
    if (!alreadyRead) {
        withStyle(
            SpanStyle(
                fontFamily = getFont(SlimeTypography.Medium()).copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp
                ).fontFamily
            )
        ) {
            append("• ")
        }
    }
    withStyle(
        SpanStyle(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = getFont(SlimeTypography.SemiBold()).copy(
                lineHeight = 26.sp
            ).fontFamily,
            fontSize = 14.sp,
        )
    ) {
        append(title)
    }
}

@Composable
fun withAuthorAndPostedTime(
    authorName: String,
    postedTime: String
) = buildAnnotatedString {
    withStyle(SpanStyle(fontFamily = getFont(SlimeTypography.SecondaryBold()).fontFamily)) {
        append(authorName)
    }
    withStyle(SpanStyle(fontFamily = getFont(SlimeTypography.SecondaryRegular()).copy(fontSize = 6.sp).fontFamily)) {
        append(" on ")
        append(postedTime)
    }
}
