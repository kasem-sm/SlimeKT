/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_auth.domain.interactors

import javax.inject.Inject
import kasem.sm.core.interfaces.Session
import kasem.sm.core.utils.toMessage
import kasem.sm.feature_auth.datasource.network.AuthApiService
import kasem.sm.feature_auth.datasource.network.request.AuthRequest
import kasem.sm.feature_auth.domain.model.AuthResult
import kasem.sm.feature_auth.domain.model.Credentials
import kasem.sm.feature_auth.domain.model.InvalidCredentialsException
import kasem.sm.feature_auth.domain.model.ServerException
import kasem.sm.feature_auth.domain.model.containsOnlyNumbers
import kasem.sm.feature_auth.domain.model.containsSpecialCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterUseCase @Inject constructor(
    private val api: AuthApiService,
    private val session: Session,
) {
    suspend fun execute(credentials: Credentials): Flow<AuthResult> = flow {
        val validationResult = validateCredentials(credentials.username, credentials.password)
        if (validationResult != null) {
            emit(validationResult)
            return@flow
        }

        val apiResponse = api.registerUser(
            request = AuthRequest(
                username = credentials.username,
                password = credentials.password,
            ),
            isUserDiscoverable = if (credentials.isAccountDiscoverable) "1" else "0"
        ).getOrElse {
            emit(AuthResult.Exception(ServerException(it.toMessage)))
            return@flow
        }

        when (apiResponse.success) {
            true -> {
                apiResponse.data?.let {
                    session.run {
                        storeUserToken(it.token)
                        storeUserId(it.userId)
                    }
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
            username.trim().length !in (4..10) -> AuthResult.Exception(InvalidCredentialsException("Username length should be between 4 to 10 characters"))
            password.trim().length !in (4..20) -> AuthResult.Exception(InvalidCredentialsException("Password length should be between 4 to 20 characters"))
            username.containsOnlyNumbers -> AuthResult.Exception(InvalidCredentialsException("Username should not only consists of numbers"))
            username.containsSpecialCharacters -> AuthResult.Exception(InvalidCredentialsException("Special characters are not allowed inside username"))
            else -> null
        }
    }
}
