/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.components

import androidx.compose.runtime.Composable
import kasem.sm.common_ui.SlimePrimaryButton

@Composable
internal fun LoginButton(
    enabled: Boolean,
    isLoading: Boolean,
    onContinueClicked: () -> Unit,
) {
    SlimePrimaryButton(
        isLoading = isLoading,
        onClick = onContinueClicked,
        enabled = enabled,
        text = "Continue"
    )
}
