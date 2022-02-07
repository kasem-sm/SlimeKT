/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.datasource.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SlimeResponse<T>(
    @SerialName("additionalMessage")
    val message: String? = null,
    @SerialName("data")
    val data: T? = null,
    @SerialName("success")
    val success: Boolean
)
