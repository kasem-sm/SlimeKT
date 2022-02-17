/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

data class SlimeFontFamily(
    val bold: FontFamily = FontFamily(Font(R.font.bold)),
    val semiBold: FontFamily = FontFamily(Font(R.font.semibold)),
    val medium: FontFamily = FontFamily(Font(R.font.medium)),
    val regular: FontFamily = FontFamily(Font(R.font.regular)),
    val italic: FontFamily = FontFamily(Font(R.font.italic)),
    val code: FontFamily = FontFamily(Font(R.font.code)),
    val secondaryBold: FontFamily = FontFamily(Font(R.font.secondary_bold)),
    val secondaryMedium: FontFamily = FontFamily(Font(R.font.secondary_medium)),
    val secondaryRegular: FontFamily = FontFamily(Font(R.font.secondary_regular))
)

val LocalSlimeFont = compositionLocalOf { SlimeFontFamily() }
