/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic

import androidx.compose.runtime.Immutable
import kasem.sm.topic.domain.model.Topic

@Immutable
data class SubscribeTopicState(
    val isLoading: Boolean = true,
    val topics: List<Topic> = emptyList(),
    val isUserAuthenticated: Boolean = false,
    val isSubscriptionInProgress: Boolean = false
) {
    companion object {
        val SubscribeTopicState.loadingStatus get() = isLoading || isSubscriptionInProgress
        val EMPTY = SubscribeTopicState()
    }
}
