/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_auth.datasource_impl.network

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kasem.sm.core.utils.withResult
import kasem.sm.feature_auth.datasource.network.AuthApiService
import kasem.sm.feature_auth.datasource.network.request.AuthRequest
import kasem.sm.feature_auth.datasource.network.response.AuthResponse
import kasem.sm.feature_auth.datasource.network.response.SlimeResponse

internal class AuthApiServiceImpl @Inject constructor(private val client: HttpClient) : AuthApiService {
    override suspend fun loginUser(request: AuthRequest): Result<SlimeResponse<AuthResponse>> {
        return withResult {
            client.post(LOGIN_ROUTE) {
                contentType(ContentType.Application.Json)
                body = request
            }
        }
    }

    override suspend fun registerUser(
        request: AuthRequest,
        isUserDiscoverable: String
    ): Result<SlimeResponse<AuthResponse>> {
        return withResult {
            client.post(REGISTER_ROUTE) {
                contentType(ContentType.Application.Json)
                body = request
                parameter("discoverable", isUserDiscoverable)
            }
        }
    }

    companion object EndPoints {
        const val LOGIN_ROUTE = "/api/auth/login"
        const val REGISTER_ROUTE = "/api/auth/register"
    }
}
