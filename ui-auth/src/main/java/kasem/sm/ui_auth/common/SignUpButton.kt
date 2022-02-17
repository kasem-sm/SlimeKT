/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont

@Composable
internal fun SignUpButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onSignUpClicked: () -> Unit,
) {
    val regularFont = getFont(SlimeTypography.Medium())
    val boldFont = getFont(SlimeTypography.Bold())
    Column(modifier) {
        TextButton(
            onClick = {
                if (enabled) {
                    onSignUpClicked()
                }
            }
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontStyle = regularFont.fontStyle,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = regularFont.fontFamily,
                            letterSpacing = 1.sp
                        )
                    ) {
                        append("Don't have an account? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontStyle = boldFont.fontStyle,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = boldFont.fontFamily,
                            letterSpacing = 1.sp
                        )
                    ) {
                        append("Sign Up")
                    }
                },
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
