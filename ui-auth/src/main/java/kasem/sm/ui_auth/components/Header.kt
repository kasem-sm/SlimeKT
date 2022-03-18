/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont

@Composable
fun Header(text1: String, text2: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = LocalSlimeFont.current.semiBold,
                    letterSpacing = 1.5.sp,
                    fontSize = 24.sp,
                )
            ) {
                append(text1)
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = LocalSlimeFont.current.medium,
                    letterSpacing = 1.sp,
                    fontSize = 14.sp
                )
            ) {
                append("\n$text2")
            }
        },
    )
}
