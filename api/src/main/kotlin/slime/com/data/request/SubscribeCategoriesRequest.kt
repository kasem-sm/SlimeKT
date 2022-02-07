package slime.com.data.request

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeCategoriesRequest(
    val categoryId: String
)
