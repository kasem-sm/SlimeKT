/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource.network.response

import androidx.annotation.Keep
import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
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
    fun toEntity(): TopicEntity {
        return TopicEntity(
            id = id,
            title = title,
            timestamp = timestamp,
            isInExplore = !hasUserSubscribed,
            isInSubscription = hasUserSubscribed,
            totalSubscribers = totalSubscribers,
        )
    }
}
