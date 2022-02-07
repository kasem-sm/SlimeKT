package slime.com.routes

import io.ktor.application.call
import io.ktor.request.receiveOrNull
import io.ktor.routing.Route
import io.ktor.routing.post
import slime.com.data.models.User
import slime.com.data.request.CreateUserAccountRequest
import slime.com.data.response.AuthResponse
import slime.com.data.response.SlimeResponse
import slime.com.service.AuthService
import slime.com.utils.ServiceResult
import slime.com.utils.respondWith
import slime.com.utils.respondWithBadRequest

fun Route.registerAuthenticationRoutes(
    service: AuthService
) {
    post("api/auth/register") {
        val response = call.receiveOrNull<CreateUserAccountRequest>() ?: kotlin.run {
            respondWithBadRequest()
            return@post
        }

        when (val result = service.validateCredentialsForRegistration(User(response.username, response.password))) {
            is ServiceResult.Success -> {
                val user = service.run { response.username.getUser() }
                val userId = user?.id ?: return@post
                val username = user.username

                when (val loginResult = service.validateCredentialsForLogin(response.username, response.password)) {
                    is ServiceResult.Success -> {
                        respondWith(
                            SlimeResponse(
                                true,
                                data = AuthResponse(
                                    userId,
                                    username,
                                    // token
                                    loginResult.message
                                )
                            )
                        )
                    }
                    is ServiceResult.Error -> respondWith(SlimeResponse<Unit>(success = false, additionalMessage = loginResult.message))
                }
            }
            is ServiceResult.Error -> respondWith<Unit>(SlimeResponse(false, result.message))
        }
    }

    post("api/auth/login") {
        val response = call.receiveOrNull<CreateUserAccountRequest>() ?: kotlin.run {
            respondWithBadRequest()
            return@post
        }

        val user = service.run { response.username.getUser() }
        user?.let {
            val userId = user.id
            val username = user.username

            when (val result = service.validateCredentialsForLogin(response.username, response.password)) {
                is ServiceResult.Success -> {
                    respondWith(
                        AuthResponse(
                            userId,
                            username,
                            result.message
                        )
                    )
                }
                is ServiceResult.Error -> respondWith(SlimeResponse<AuthResponse>(false, result.message, null))
            }
        } ?: kotlin.run {
            respondWith(SlimeResponse<AuthResponse>(false, "No user with the following username exists", null))
        }
    }
}
