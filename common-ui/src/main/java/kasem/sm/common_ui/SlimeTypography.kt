/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

private val Bold = FontFamily(Font(R.font.bold))
private val SemiBold = FontFamily(Font(R.font.semibold))
private val Medium = FontFamily(Font(R.font.medium))
private val Regular = FontFamily(Font(R.font.regular))
private val Italic = FontFamily(Font(R.font.italic))
private val Code = FontFamily(Font(R.font.code))

private val SecondaryBold = FontFamily(Font(R.font.secondary_bold))
private val SecondarySemiBold = FontFamily(Font(R.font.secondary_semibold))
private val SecondaryMedium = FontFamily(Font(R.font.secondary_medium))
private val SecondaryRegular = FontFamily(Font(R.font.secondary_regular))

sealed class SlimeTypography(
    val font: FontFamily,
    val fontSize: TextUnit,
    val letterSpacing: TextUnit,
) {
    class Bold(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = Bold, fontSize, letterSpacing)

    class SemiBold(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = SemiBold, fontSize, letterSpacing)

    class Medium(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = Medium, fontSize, letterSpacing)

    class Regular(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = Regular, fontSize, letterSpacing)

    class Italic(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 1.sp) :
        SlimeTypography(font = Italic, fontSize, letterSpacing)

    class SecondaryBold(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = SecondaryBold, fontSize, letterSpacing)

    class SecondarySemiBold(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = SecondarySemiBold, fontSize, letterSpacing)

    class SecondaryMedium(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = SecondaryMedium, fontSize, letterSpacing)

    class SecondaryRegular(fontSize: TextUnit = 12.sp, letterSpacing: TextUnit = 0.5.sp) :
        SlimeTypography(font = SecondaryRegular, fontSize, letterSpacing)

    class CodeRegular(fontSize: TextUnit = 14.sp) :
        SlimeTypography(font = Code, fontSize, TextStyle.Default.letterSpacing)
}

fun getFont(typography: SlimeTypography): TextStyle {
    return TextStyle(
        fontFamily = typography.font,
        letterSpacing = typography.letterSpacing,
        fontSize = typography.fontSize
    )
}
