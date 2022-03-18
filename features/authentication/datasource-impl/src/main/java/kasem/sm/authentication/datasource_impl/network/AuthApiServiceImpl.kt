/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.datasource_impl.network

import com.slime.auth_api.AuthManager
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import javax.inject.Inject
import kasem.sm.authentication.datasource.network.AuthApiService
import kasem.sm.authentication.datasource.network.response.AuthResponse
import kasem.sm.authentication.datasource.network.response.SlimeResponse
import kasem.sm.core.utils.withResult

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
            }
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
            }
        }
    }

    override suspend fun checkAuthenticationState(): Result<SlimeResponse<Boolean>> {
        return withResult {
            client.get(AUTHENTICATION_ROUTE) {
                parameter("userId", authManager.getUserId())
            }
        }
    }

    companion object EndPoints {
        const val LOGIN_ROUTE = "/api/auth/login"
        const val REGISTER_ROUTE = "/api/auth/register"
        const val AUTHENTICATION_ROUTE = "/api/auth/authenticate"
    }
}
