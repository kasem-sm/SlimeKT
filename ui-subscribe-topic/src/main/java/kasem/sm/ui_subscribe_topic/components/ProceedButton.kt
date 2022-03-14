/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kasem.sm.common_ui.R.drawable
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.ui_subscribe_topic.SubscribeTopicState
import kasem.sm.ui_subscribe_topic.SubscribeTopicState.Companion.isListEmpty
import kasem.sm.ui_subscribe_topic.SubscribeTopicState.Companion.loadingStatus

@Composable
fun ProceedButton(
    state: SubscribeTopicState,
    showAuthenticationSheet: () -> Unit,
    navigateBack: () -> Unit,
    saveRecommendedValues: () -> Unit
) {
    SlimePrimaryButton(
        isLoading = state.loadingStatus,
        text = if (!state.isUserAuthenticated) {
            "Please Sign In to continue"
        } else if (state.isListEmpty) "Go Back"
        else stringResource(string.continue_btn),
        onClick = {
            if (!state.isUserAuthenticated) {
                showAuthenticationSheet()
            } else if (state.isListEmpty) {
                navigateBack()
            } else saveRecommendedValues()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = !state.loadingStatus,
        backgroundColor = MaterialTheme.colorScheme.primary,
        trailingIcon = drawable.ic_lock elseNull { !state.isUserAuthenticated }
    )
}

infix fun <T> T.elseNull(predicate: () -> Boolean): T? {
    return if (predicate()) this else null
}
