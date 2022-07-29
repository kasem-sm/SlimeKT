/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.datasource_impl.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kasem.sm.auth_api.AuthManager
import kasem.sm.auth_api.ID
import kasem.sm.authentication.datasource.network.AuthApiService
import kasem.sm.authentication.datasource.network.response.AuthResponse
import kasem.sm.authentication.datasource.network.response.SlimeResponse
import kasem.sm.core.utils.userIdParam
import kasem.sm.core.utils.withResult
import javax.inject.Inject

internal class AuthApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val authManager: AuthManager,
) : AuthApiService {
    override suspend fun loginUser(
        username: String,
        password: String,
    ): Result<SlimeResponse<AuthResponse>> {
        return withResult {
            client.post(LOGIN_ROUTE) {
                parameter("username", username)
                parameter("password", password)
            }.body()
        }
    }

    override suspend fun registerUser(
        username: String,
        password: String,
        isUserDiscoverable: String
    ): Result<SlimeResponse<AuthResponse>> {
        return withResult {
            client.post(REGISTER_ROUTE) {
                parameter("username", username)
                parameter("password", password)
                parameter("discoverable", isUserDiscoverable)
            }.body()
        }
    }

    override suspend fun checkAuthenticationState(): Result<SlimeResponse<Boolean>> {
        return withResult {
            client.get(AUTHENTICATION_ROUTE) {
                userIdParam(id = authManager.getUserData(ID))
            }.body()
        }
    }

    companion object EndPoints {
        const val LOGIN_ROUTE = "/api/auth/login"
        const val REGISTER_ROUTE = "/api/auth/register"
        const val AUTHENTICATION_ROUTE = "/api/auth/authenticate"
    }
}
