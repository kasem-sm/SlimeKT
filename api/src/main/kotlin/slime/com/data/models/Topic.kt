/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Topic(
    val name: String,
    val totalSubscribers: Int = 0,
    val hasUserSubscribed: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val isInRecommendation: Boolean = false,
    @BsonId
    val id: String = ObjectId().toString(),
)
