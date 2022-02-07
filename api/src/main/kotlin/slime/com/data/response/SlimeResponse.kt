package slime.com.data.response

import kotlinx.serialization.Serializable

@Serializable
data class SlimeResponse<T>(
    val success: Boolean,
    var additionalMessage: String? = null,
    val data: T? = null
)
