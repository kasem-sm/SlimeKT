/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.utils

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.util.pipeline.PipelineContext
import slime.com.data.response.AuthResponse
import slime.com.data.response.SlimeResponse
import slime.com.plugins.userId
import slime.com.service.UserService

suspend fun PipelineContext<Unit, ApplicationCall>.loginUser(
    validateLogin: suspend (username: String, password: String) -> ServiceResult,
    username: String,
    password: String,
    userId: String
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

suspend inline fun PipelineContext<Unit, ApplicationCall>.getUserId(userService: UserService, doWork: (String) -> Unit) {
    val userId = call.userId ?: return
    val currentUser = userService.run { userId.getUserById() }
    currentUser?.let { user ->
        doWork(user.id)
    } ?: kotlin.run {
        respondWith(SlimeResponse<AuthResponse>(false, "You don't exists", null))
    }
}

fun PipelineContext<Unit, ApplicationCall>.getUserId(): String? {
    return call.parameters["userId"]
}
