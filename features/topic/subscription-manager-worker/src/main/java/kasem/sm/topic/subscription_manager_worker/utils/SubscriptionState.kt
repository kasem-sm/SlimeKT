/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.subscription_manager_worker.utils

enum class SubscriptionState {
    SUBSCRIBED, UNSUBSCRIBED, UNKNOWN
}

fun String.getSubscriptionState(): SubscriptionState {
    return when {
        this == "Subscribed" -> SubscriptionState.SUBSCRIBED
        this == "Unsubscribed" -> SubscriptionState.UNSUBSCRIBED
        else -> SubscriptionState.UNKNOWN
    }
}
