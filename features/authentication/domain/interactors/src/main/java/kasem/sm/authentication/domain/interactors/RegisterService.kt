/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.interactors

import com.slime.auth_api.AuthManager
import javax.inject.Inject
import kasem.sm.authentication.datasource.network.AuthApiService
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.AuthResult.Companion.toInvalidCredentialsException
import kasem.sm.authentication.domain.model.Credentials
import kasem.sm.authentication.domain.model.ServerException
import kasem.sm.authentication.domain.model.containsOnlyNumbers
import kasem.sm.authentication.domain.model.containsSpecialCharacters
import kasem.sm.core.utils.toMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterService @Inject constructor(
    private val api: AuthApiService,
    private val authManager: AuthManager,
) {
    suspend fun execute(credentials: Credentials): Flow<AuthResult> = flow {
        val validationResult = validateCredentials(credentials.username, credentials.password)
        if (validationResult != null) {
            emit(validationResult)
            return@flow
        }

        val apiResponse = api.registerUser(
            username = credentials.username.trim(),
            password = credentials.password.trim(),
            isUserDiscoverable = if (credentials.isAccountDiscoverable) "1" else "0"
        ).getOrElse {
            emit(AuthResult.Exception(ServerException(it.toMessage)))
            return@flow
        }

        when (apiResponse.success) {
            true -> {
                apiResponse.data?.let {
                    authManager.onNewSession(
                        AuthManager.SlimeSession(
                            token = it.token,
                            userId = it.userId
                        )
                    )
                } ?: return@flow
                emit(AuthResult.Success)
            }
            false -> emit(
                AuthResult.Exception(
                    ServerException(
                        apiResponse.message ?: "Server Failure"
                    )
                )
            )
        }
    }

    private fun validateCredentials(
        username: String,
        password: String,
    ): AuthResult? {
        val isUsernameEmpty = username.isEmpty()
        val isPasswordEmpty = password.isEmpty()
        return when {
            username.isEmpty() || password.isEmpty() -> AuthResult.EmptyCredentials(
                isUsernameEmpty = isUsernameEmpty,
                isPasswordEmpty = isPasswordEmpty,
            )
            username.trim().length !in (4..10) -> "Username length should be between 4 to 10 characters".toInvalidCredentialsException()
            password.trim().length !in (4..20) -> "Password length should be between 4 to 20 characters".toInvalidCredentialsException()
            username.containsOnlyNumbers -> "Username should not only consists of numbers".toInvalidCredentialsException()
            username.containsSpecialCharacters -> "Special characters are not allowed inside username".toInvalidCredentialsException()
            else -> null
        }
    }
}
