/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.common_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont

@Composable
fun withAuthorAndPostedTime(
    authorName: String,
    postedTime: String
): AnnotatedString = buildAnnotatedString {
    withStyle(SpanStyle(fontFamily = LocalSlimeFont.current.secondaryBold)) {
        append(authorName)
    }
    withStyle(SpanStyle(fontFamily = LocalSlimeFont.current.secondaryMedium, fontSize = 12.sp)) {
        append(" on ")
        append(postedTime)
    }
}
