/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource.network.response

import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val title: String,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("totalSubscribers")
    val totalSubscribers: Int,
    @SerialName("hasUserSubscribed")
    val hasUserSubscribed: Boolean
) {
    fun toEntity(isInExplore: Boolean = false): TopicEntity {
        return TopicEntity(
            id = id,
            title = title,
            timestamp = timestamp,
            isInSubscription = hasUserSubscribed,
            isInExplore = isInExplore,
            totalSubscribers = totalSubscribers,
        )
    }
}
