/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@kotlinx.serialization.Serializable
data class RecommendedTopic(
    val userId: String? = null,
    val topicId: String,
    val topicName: String,
    @BsonId
    val id: String = ObjectId().toString(),
)
