package slime.com.routes

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.util.pipeline.PipelineContext
import slime.com.data.response.AuthResponse
import slime.com.data.response.SlimeResponse
import slime.com.service.UserService
import slime.com.utils.ServiceResult
import slime.com.utils.getUserId
import slime.com.utils.respondWith
import slime.com.utils.respondWithBadRequest
import java.lang.NumberFormatException

fun Route.registerAuthenticationRoutes(
    service: UserService
) {
    post("api/auth/register") {
        val username = call.parameters["username"] ?: return@post
        val password = call.parameters["password"] ?: return@post

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
                username = username,
                password = password,
                isUserDiscoverable = isUserDiscoverable != 0
            )
        ) {
            is ServiceResult.Success -> {
                val user = service.run { username.getUser() }
                user?.let {
                    loginUser(
                        validateLogin = service::validateCredentialsForLogin,
                        username = username,
                        password = password,
                        userId = it.id,
                    )
                } ?: kotlin.run { return@post }
            }
            is ServiceResult.Error -> respondWith(
                SlimeResponse<AuthResponse>(
                    success = false,
                    additionalMessage = result.message
                )
            )
        }
    }

    post("api/auth/login") {
        val username = call.parameters["username"] ?: return@post
        val password = call.parameters["password"] ?: return@post

        val user = service.run { username.getUser() }
        user?.let {
            loginUser(
                validateLogin = service::validateCredentialsForLogin,
                username = username,
                password = password,
                userId = it.id,
            )
        } ?: kotlin.run {
            respondWith(
                SlimeResponse<AuthResponse>(
                    success = false,
                    additionalMessage = "No user with the following username exists",
                    data = null
                )
            )
        }
    }

    authenticate {
        get("api/auth/randomAuthor") {
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

private suspend fun PipelineContext<Unit, ApplicationCall>.loginUser(
    validateLogin: suspend (username: String, password: String) -> ServiceResult,
    username: String,
    password: String,
    userId: String,
) {
    when (val result = validateLogin(username, password)) {
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
}
