/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.common_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.util.TestTags

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onContinueClicked: () -> Unit,
) {
    SlimePrimaryButton(
        isLoading = isLoading,
        onClick = onContinueClicked,
        enabled = !isLoading,
        text = stringResource(id = string.continue_btn),
        modifier = modifier
            .semantics { testTag = TestTags.LoginContent.LOGIN_BUTTON }
    )
}
