package slime.com.data.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCategoryRequest(
    val name: String,
)
