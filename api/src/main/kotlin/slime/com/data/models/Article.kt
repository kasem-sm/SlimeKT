/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Article(
    val title: String,
    val description: String,
    val featuredImage: String,
    val author: String,
    val topic: String,
    val timestamp: Long = System.currentTimeMillis(),
    @BsonId
    val id: Int = ObjectId().timestamp
)
