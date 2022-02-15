/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import kasem.sm.ui_article_list.ListState

@Composable
internal fun SubscribeView(
    state: ListState,
    updateSubscription: (onSuccess: () -> Unit) -> Unit,
    isUserAuthenticated: Boolean,
    showAuthenticationSheet: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val hasUserSubscribed = remember {
        mutableStateOf(state.category?.hasUserSubscribed ?: false)
    }

    state.category?.let { category ->
        SubscribeButtonAndHeader(
            category = category,
            onClick = {
                if (isUserAuthenticated) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    updateSubscription {
                        hasUserSubscribed.value = !hasUserSubscribed.value
                    }
                } else showAuthenticationSheet()
            },
            isSubscriptionButtonActive = hasUserSubscribed.value,
            isSubscriptionInProgress = state.isSubscriptionInProgress
        )
    }
}
