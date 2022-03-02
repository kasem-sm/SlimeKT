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
)
