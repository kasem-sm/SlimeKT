/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.SlimeTextField
import kasem.sm.common_ui.SlimeTypography
import kasem.sm.common_ui.getFont

@Composable
fun UsernameField(
    text: String,
    onUsernameChanged: (String) -> Unit,
    enabled: Boolean = true,
) {
    SlimeTextField(
        input = text,
        onTextChange = onUsernameChanged,
        enabled = enabled,
        leadingIcon = Icons.Default.AccountCircle,
        placeholderContent = {
            Text(
                text = "Username",
                style = getFont(SlimeTypography.Medium(14.sp)),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}
