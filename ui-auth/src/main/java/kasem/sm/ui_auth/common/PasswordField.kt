/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimeTextField

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    text: String,
    onPasswordChanged: (String) -> Unit,
    enabled: Boolean = true,
    passwordToggle: Boolean,
    onPasswordToggleClick: (Boolean) -> Unit,
    onDoneClicked: KeyboardActionScope.() -> Unit,
) {
    SlimeTextField(
        modifier = modifier,
        input = text,
        onTextChange = onPasswordChanged,
        enabled = enabled,
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
                    if (enabled) onPasswordToggleClick(!passwordToggle)
                }
            ) {
                Icon(
                    imageVector = passwordToggle.getTrailingIconAccordingly(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(bottom = 2.dp),
                    tint = passwordToggle.getTintAccordingly()
                )
            }
        },
        placeholderContent = {
            Text(
                text = stringResource(id = string.password),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
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
        visualTransformation = if (passwordToggle) {
            PasswordVisualTransformation()
        } else VisualTransformation.None
    )
}

@Composable
fun Boolean.getTintAccordingly(): Color {
    return if (this) MaterialTheme.colorScheme.onSurfaceVariant else
        MaterialTheme.colorScheme.primary
}

@Composable
fun Boolean.getTrailingIconAccordingly(): ImageVector {
    return if (this) Icons.Default.VisibilityOff else
        Icons.Default.Visibility
}