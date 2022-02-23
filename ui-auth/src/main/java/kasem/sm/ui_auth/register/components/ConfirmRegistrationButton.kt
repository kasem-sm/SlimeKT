/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.ui_auth.common.AuthState

@Composable
internal fun ConfirmRegistrationButton(
    state: AuthState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    SlimePrimaryButton(
        text = stringResource(id = R.string.confirm_reg),
        onClick = onClick,
        enabled = !state.isLoading,
        isLoading = state.isLoading,
        modifier = modifier
    )
}
