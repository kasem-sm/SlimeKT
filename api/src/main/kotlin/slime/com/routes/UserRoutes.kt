/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.response.AuthResponse
import slime.com.data.response.SlimeResponse
import slime.com.service.UserService
import slime.com.utils.ServiceResult
import slime.com.utils.get
import slime.com.utils.getUserId
import slime.com.utils.loginUser
import slime.com.utils.respondWith
import slime.com.utils.respondWithBadRequest

fun Route.registerAuthenticationRoutes(
    service: UserService
) {
    post("api/auth/register") {
        val username = get("username") ?: return@post
        val password = get("password") ?: return@post

        val isUserDiscoverable = try {
            get("discoverable")?.toInt() ?: kotlin.run {
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
        val username = get("username") ?: return@post
        val password = get("password") ?: return@post

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

    get("/api/auth/authenticate") {
        val userId = getUserId()

        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest)
        } else {
            val userExists = service.run {
                userId.getUserById() != null
            }

            call.respond(
                message = when (userExists) {
                    false -> HttpStatusCode.BadRequest
                    else -> HttpStatusCode.OK
                }
            )
        }
    }
}
