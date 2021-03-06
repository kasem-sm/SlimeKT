/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kasem.sm.authentication.domain.model.AuthState
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimeTextField
import kasem.sm.common_ui.util.TestTags
import kasem.sm.common_ui.withScale

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    state: AuthState,
    onPasswordChanged: (String) -> Unit,
    onPasswordToggleClick: (Boolean) -> Unit,
    onDoneClicked: KeyboardActionScope.() -> Unit,
) {
    SlimeTextField(
        modifier = modifier
            .semantics { testTag = TestTags.LoginContent.PASSWORD_FIELD },
        input = state.password,
        onTextChange = onPasswordChanged,
        enabled = !state.isLoading,
        leadingIconContent = {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .padding(bottom = 2.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIconContent = {
            IconButton(
                onClick = {
                    if (!state.isLoading) onPasswordToggleClick(!state.passwordVisibility)
                }
            ) {
                Icon(
                    imageVector = state.passwordVisibility.getTrailingIconAccordingly(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(bottom = 2.dp),
                    tint = MaterialTheme.getIconTintAccordingly(boolean = state.passwordVisibility)
                )
            }
        },
        placeholderContent = {
            Text(
                text = stringResource(id = string.password),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.withScale(),
                fontFamily = LocalSlimeFont.current.medium
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = onDoneClicked
        ),
        visualTransformation = if (state.passwordVisibility) {
            VisualTransformation.None
        } else PasswordVisualTransformation()
    )
}

@Composable
fun MaterialTheme.getIconTintAccordingly(boolean: Boolean): Color {
    return if (boolean) colorScheme.primary else colorScheme.onSurfaceVariant
}

@Composable
fun Boolean.getTrailingIconAccordingly(): ImageVector {
    return if (this) Icons.Default.Visibility else
        Icons.Default.VisibilityOff
}
