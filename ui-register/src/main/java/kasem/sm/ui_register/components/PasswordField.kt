/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_register.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTextField
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont

@Composable
fun PasswordField(
    text: String,
    onPasswordChanged: (String) -> Unit,
    enabled: Boolean = true,
    passwordToggle: Boolean,
    onPasswordToggleClick: (Boolean) -> Unit,
) {
    SlimeTextField(
        input = text,
        onTextChange = onPasswordChanged,
        enabled = enabled,
        leadingIcon = Icons.Default.Password,
        isFieldTypePassword = true,
        passwordToggle = passwordToggle,
        onPasswordToggleClick = onPasswordToggleClick,
        placeholderContent = {
            Text(
                text = "Password",
                style = getFont(SlimeTypography.Medium(14.sp)),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}
