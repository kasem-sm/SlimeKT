/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.interactors

import javax.inject.Inject
import kasem.sm.authentication.datasource.network.AuthApiService
import kasem.sm.authentication.domain.model.ApiResponseConstants.LOGIN_FAILED_INVALID_CREDENTIALS
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kasem.sm.authentication.domain.model.InvalidCredentialsException
import kasem.sm.authentication.domain.model.ServerException
import kasem.sm.core.interfaces.AuthManager
import kasem.sm.core.utils.toMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * [execute] function takes user's credentials and tries to log in.
 *
 * @return [AuthResult] that contains whether the attempt to login
 * by the user was successful or not.
 */

class LoginUseCase @Inject constructor(
    private val api: AuthApiService,
    private val authManager: AuthManager,
) {
    fun execute(credentials: Credentials): Flow<AuthResult> = flow {
        val validationResult = validateCredentials(credentials)
        if (validationResult != null) {
            emit(validationResult)
            return@flow
        }

        val apiResponse = api.loginUser(
            username = credentials.username,
            password = credentials.password
        ).getOrElse {
            emit(AuthResult.Exception(ServerException(it.toMessage)))
            return@flow
        }

        when (apiResponse.success) {
            true -> {
                apiResponse.data?.let {
                    AuthManager.SlimeSession(
                        token = it.token,
                        userId = it.userId
                    )
                } ?: return@flow
                emit(AuthResult.Success)
            }
            false -> emit(loginResultForFailure(apiResponse.message))
        }
    }

    private fun validateCredentials(credentials: Credentials): AuthResult? {
        val emptyUsername = credentials.username.isEmpty()
        val emptyPassword = credentials.password.isEmpty()

        return when {
            (emptyUsername || emptyPassword) -> {
                AuthResult.EmptyCredentials(
                    isUsernameEmpty = emptyUsername,
                    isPasswordEmpty = emptyPassword
                )
            }
            else -> null
        }
    }

    private fun loginResultForFailure(error: String?): AuthResult.Exception {
        return when (error) {
            LOGIN_FAILED_INVALID_CREDENTIALS -> AuthResult.Exception(
                InvalidCredentialsException()
            )
            else -> AuthResult.Exception(ServerException(error ?: "Server Failure"))
        }
    }
}
