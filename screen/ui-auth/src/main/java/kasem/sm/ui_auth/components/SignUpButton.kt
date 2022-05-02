/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.util.TestTags

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onSignUpClicked: () -> Unit,
) {
    Column(modifier.semantics { testTag = TestTags.LoginContent.SIGN_IN_BUTTON }) {
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
