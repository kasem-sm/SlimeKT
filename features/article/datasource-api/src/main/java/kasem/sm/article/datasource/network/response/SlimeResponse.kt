/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.network.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SlimeResponse<T>(
    @SerialName("additionalMessage")
    val message: String? = null,
    @SerialName("data")
    val data: T? = null,
    @SerialName("success")
    val success: Boolean
)
