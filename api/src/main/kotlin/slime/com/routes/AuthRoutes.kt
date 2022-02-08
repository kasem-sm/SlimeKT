package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.request.CreateUserAccountRequest
import slime.com.data.response.AuthResponse
import slime.com.data.response.SlimeResponse
import slime.com.service.AuthService
import slime.com.utils.ServiceResult
import slime.com.utils.getUserId
import slime.com.utils.respondWith
import slime.com.utils.respondWithBadRequest
import java.lang.NumberFormatException

fun Route.registerAuthenticationRoutes(
    service: AuthService
) {
    post("api/auth/register") {
        val response = call.receiveOrNull<CreateUserAccountRequest>() ?: kotlin.run {
            respondWithBadRequest()
            return@post
        }

        val isUserDiscoverable = try {
            call.parameters["discoverable"]?.toInt() ?: kotlin.run {
                respondWithBadRequest()
                return@post
            }
        } catch (exception: NumberFormatException) {
            respondWith("Either 1 or 0 is allowed in discoverable parameter")
            return@post
        }

        when (
            val result = service.validateCredentialsForRegistration(
                username = response.username,
                password = response.password,
                isUserDiscoverable = isUserDiscoverable != 0
            )
        ) {
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

    authenticate {
        get("api/auth/randomUsers") {
            getUserId(service) { id ->
                val randomUser = service.getRandomUser(excludedUserId = id)
                respondWith(randomUser)
            }
        }
    }

    authenticate {
        get("/api/auth/authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
