/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimePrimaryButton

@Composable
internal fun LoginButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    isLoading: Boolean,
    onContinueClicked: () -> Unit,
) {
    SlimePrimaryButton(
        isLoading = isLoading,
        onClick = onContinueClicked,
        enabled = enabled,
        text = stringResource(id = string.login_dont_have_account),
        modifier = modifier
    )
}
