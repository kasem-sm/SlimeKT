/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.model

data class Topic(
    val id: String,
    val title: String,
    val timestamp: Long = -1,
    val isSelected: Boolean = false,
    val totalSubscribers: Int = 0,
    val hasUserSubscribed: Boolean = false,
)
