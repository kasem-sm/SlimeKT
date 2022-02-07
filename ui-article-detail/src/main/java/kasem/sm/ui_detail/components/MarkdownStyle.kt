/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont
import kasem.sm.feature_article.markdown.handler.MarkdownStyle

internal val headingLetterSpace = 1.5.sp
internal val boldFontFamily = getFont(SlimeTypography.Bold(letterSpacing = headingLetterSpace)).fontFamily
internal val semiBoldFontFamily =
    getFont(SlimeTypography.SemiBold(letterSpacing = headingLetterSpace)).fontFamily
internal val mediumFontFamily =
    getFont(SlimeTypography.Medium(letterSpacing = headingLetterSpace)).fontFamily
internal val regularFontFamily =
    getFont(SlimeTypography.Medium(letterSpacing = headingLetterSpace)).fontFamily

@Composable
internal fun paragraphSpanStyle(): SpanStyle {
    return SpanStyle(
        fontFamily = SlimeTypography.SemiBold(letterSpacing = 1.sp).font,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 16.sp,
    )
}

@Composable
internal fun slimeMarkdownStyle(): MarkdownStyle {
    return MarkdownStyle(
        blockQuoteSpanStyle = getFont(SlimeTypography.Italic(fontSize = 18.sp)).toSpanStyle(),
        blockQuoteBlockColor = MaterialTheme.colorScheme.onBackground,
        blockQuoteTextColor = MaterialTheme.colorScheme.onBackground,
        paragraphSpanStyle = paragraphSpanStyle(),
        codeFontStyle = getFont(SlimeTypography.CodeRegular()),
        codeBlockBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        codeTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        h1 = MaterialTheme.typography.headlineLarge.copy(fontFamily = boldFontFamily),
        h2 = MaterialTheme.typography.headlineMedium.copy(fontFamily = semiBoldFontFamily),
        h3 = MaterialTheme.typography.headlineSmall.copy(fontFamily = mediumFontFamily),
        h4 = MaterialTheme.typography.titleLarge.copy(fontFamily = mediumFontFamily),
        h5 = MaterialTheme.typography.titleMedium.copy(fontFamily = mediumFontFamily),
        h6 = MaterialTheme.typography.titleSmall.copy(fontFamily = regularFontFamily),
    )
}
