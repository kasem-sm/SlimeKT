package slime.com.data.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserAccountRequest(
    val username: String,
    val password: String
)
