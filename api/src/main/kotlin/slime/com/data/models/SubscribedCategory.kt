package slime.com.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SubscribedCategory(
    val userId: String,
    val categoryId: String
)
