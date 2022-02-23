/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.model

data class Topic(
    val id: String,
    val title: String,
    val timestamp: Long,
    val isSelected: Boolean = false,
    val totalSubscribers: Int,
    val hasUserSubscribed: Boolean,
) {
    companion object {
        val dummy
            get() = Topic(
                id = "-1",
                title = "dummy_topic",
                timestamp = 1L,
                totalSubscribers = 0,
                hasUserSubscribed = false,
            )
    }
}
