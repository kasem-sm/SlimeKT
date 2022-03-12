/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.response

import kotlinx.serialization.Serializable

@Serializable
data class SlimeResponse<T>(
    val success: Boolean,
    val additionalMessage: String? = null,
    val data: T? = null
)
