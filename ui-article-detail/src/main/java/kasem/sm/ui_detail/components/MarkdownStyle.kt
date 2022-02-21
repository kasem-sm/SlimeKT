/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kasem.sm.article.markdown.handler.MarkdownStyle
import kasem.sm.common_ui.LocalSlimeFont

@Composable
internal fun paragraphSpanStyle(): SpanStyle {
    return SpanStyle(
        fontFamily = LocalSlimeFont.current.medium,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 15.sp,
        letterSpacing = 0.5.sp
    )
}

@Composable
internal fun slimeMarkdownStyle(): MarkdownStyle {
    return MarkdownStyle(
        blockQuoteSpanStyle = TextStyle(fontFamily = LocalSlimeFont.current.italic).toSpanStyle(),
        blockQuoteBlockColor = MaterialTheme.colorScheme.onBackground,
        blockQuoteTextColor = MaterialTheme.colorScheme.onBackground,
        paragraphSpanStyle = paragraphSpanStyle(),
        codeFontStyle = TextStyle(fontFamily = LocalSlimeFont.current.code),
        codeBlockBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        codeTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        h1 = MaterialTheme.typography.headlineLarge.copy(fontFamily = LocalSlimeFont.current.bold),
        h2 = MaterialTheme.typography.headlineMedium.copy(fontFamily = LocalSlimeFont.current.semiBold),
        h3 = MaterialTheme.typography.headlineSmall.copy(fontFamily = LocalSlimeFont.current.medium),
        h4 = MaterialTheme.typography.titleLarge.copy(fontFamily = LocalSlimeFont.current.medium),
        h5 = MaterialTheme.typography.titleMedium.copy(fontFamily = LocalSlimeFont.current.medium),
        h6 = MaterialTheme.typography.titleSmall.copy(fontFamily = LocalSlimeFont.current.regular),
    )
}