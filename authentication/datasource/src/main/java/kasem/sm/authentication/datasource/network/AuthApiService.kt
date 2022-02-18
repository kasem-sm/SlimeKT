/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.datasource.network

import kasem.sm.authentication.datasource.network.request.AuthRequest
import kasem.sm.authentication.datasource.network.response.AuthResponse
import kasem.sm.authentication.datasource.network.response.SlimeResponse

interface AuthApiService {

    /**
     * function to authenticate user at backend
     * @return [AuthResponse] wrapped in of [SlimeResponse]
     */

    suspend fun loginUser(
        request: AuthRequest
    ): Result<SlimeResponse<AuthResponse>>

    /**
     * function to register a new user at backend
     * @return [AuthResponse] wrapped in of [SlimeResponse]
     */
    suspend fun registerUser(
        request: AuthRequest,
        isUserDiscoverable: String
    ): Result<SlimeResponse<AuthResponse>>
}
