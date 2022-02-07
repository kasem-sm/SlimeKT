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
import kasem.sm.ui_article_list.ListViewState

@Composable
internal fun SubscribeView(
    viewState: ListViewState,
    updateSubscription: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val isSubscribed = viewState.category?.hasUserSubscribed ?: false
    val isButtonOfSubscription = remember {
        mutableStateOf(isSubscribed)
    }

    viewState.category?.let { category ->
        SubscribeButtonAndHeader(
            category = category,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                updateSubscription()
                isButtonOfSubscription.value = !isButtonOfSubscription.value
            },
            isSubscriptionButtonActive = isButtonOfSubscription.value
        )
    }
}
