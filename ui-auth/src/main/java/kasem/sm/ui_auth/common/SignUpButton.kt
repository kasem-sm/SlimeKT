/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont

@Composable
internal fun SignUpButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onSignUpClicked: () -> Unit,
) {
    Column(modifier) {
        TextButton(
            onClick = {
                if (enabled) onSignUpClicked()
            }
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = LocalSlimeFont.current.medium,
                            letterSpacing = 1.sp
                        )
                    ) {
                        append("Don't have an account? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = LocalSlimeFont.current.bold,
                            letterSpacing = 1.sp
                        )
                    ) {
                        append("Sign Up")
                    }
                },
            )
        }
    }
}
