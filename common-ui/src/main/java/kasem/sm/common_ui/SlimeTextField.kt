/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A Custom Text Input Field for Slime that can be
 * customized.
 *
 * @param input the value of the text field [should be a state]
 * @param onTextChange a callback invoked when a user updates the text field.
 * @param enabled the state of the text field
 *
 */

@Composable
fun SlimeTextField(
    modifier: Modifier = Modifier,
    input: String,
    onTextChange: (String) -> Unit = {},
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    isFieldTypePassword: Boolean = false,
    passwordToggle: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    trailingIcon: ImageVector? = null,
    onTrailingIconClicked: () -> Unit = {},
    imeAction: ImeAction? = null,
    onSearch: () -> Unit = {},
    placeholderContent: @Composable () -> Unit = {},
) {
    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = input,
            onValueChange = onTextChange,
            enabled = enabled,
            singleLine = singleLine,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            textStyle = getFont(SlimeTypography.Medium(14.sp)),
            visualTransformation = if (isFieldTypePassword && passwordToggle) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isFieldTypePassword) KeyboardType.Password else KeyboardType.Text,
                imeAction = imeAction ?: ImeAction.Default
            ),
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = leadingIcon ?: return@IconButton,
                        contentDescription = null,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(bottom = 2.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            trailingIcon = {
                if (isFieldTypePassword) {
                    IconButton(
                        onClick = {
                            if (enabled) onPasswordToggleClick(!passwordToggle)
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordToggle) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            modifier = Modifier
                                .size(22.dp)
                                .padding(bottom = 2.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    IconButton(onClick = onTrailingIconClicked) {
                        Icon(
                            imageVector = trailingIcon ?: return@IconButton,
                            contentDescription = null,
                            modifier = Modifier
                                .size(22.dp)
                                .padding(bottom = 2.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            placeholder = placeholderContent,
            colors = textFieldColors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            keyboardActions = if (imeAction == ImeAction.Search) {
                KeyboardActions(onSearch = {
                    onSearch()
                })
            } else KeyboardActions.Default
        )
        AnimatedVisibility(visible = errorMessage != null) {
            Text(
                text = errorMessage ?: return@AnimatedVisibility,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(vertical = 5.dp),
                style = getFont(SlimeTypography.Regular()),
            )
        }
    }
}
