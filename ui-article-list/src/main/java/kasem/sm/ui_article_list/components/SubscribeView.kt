/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import kasem.sm.ui_article_list.ListState

@Composable
internal fun SubscribeView(
    modifier: Modifier = Modifier,
    state: ListState,
    updateSubscription: () -> Unit,
    showAuthenticationSheet: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    state.topic?.let { topic ->
        SubscribeButtonAndHeader(
            modifier = modifier
                .semantics { testTag = "subscribeButtonAndHeader" },
            topic = topic,
            onClick = {
                if (state.isUserAuthenticated) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    updateSubscription()
                } else showAuthenticationSheet()
            },
            isSubscriptionButtonActive = topic.hasUserSubscribed,
            isSubscriptionInProgress = state.isSubscriptionInProgress
        )
    }
}
