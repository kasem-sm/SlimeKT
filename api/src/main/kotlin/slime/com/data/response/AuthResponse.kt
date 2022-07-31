/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("userId")
    val userId: String,
    @SerialName("username")
    val username: String,
    @SerialName("token")
    val token: String
)
