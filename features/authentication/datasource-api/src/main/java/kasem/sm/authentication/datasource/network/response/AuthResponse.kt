/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.datasource.network.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AuthResponse(
    @SerialName("userId")
    val userId: String,
    @SerialName("username")
    val username: String,
    @SerialName("token")
    val token: String,
)
