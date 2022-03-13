/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    val username: String,
    val password: String,
    val isUserDiscoverable: Boolean = true,
    @BsonId
    val id: String = ObjectId().toString()
)
