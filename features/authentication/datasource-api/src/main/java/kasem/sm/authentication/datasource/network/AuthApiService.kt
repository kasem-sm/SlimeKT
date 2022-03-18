/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.datasource.network

import kasem.sm.authentication.datasource.network.response.AuthResponse
import kasem.sm.authentication.datasource.network.response.SlimeResponse

interface AuthApiService {

    /**
     * function to authenticate user at backend
     * @return [AuthResponse] wrapped in of [SlimeResponse]
     */

    suspend fun loginUser(
        username: String,
        password: String,
    ): Result<SlimeResponse<AuthResponse>>

    /**
     * function to register a new user at backend
     * @return [AuthResponse] wrapped in of [SlimeResponse]
     */
    suspend fun registerUser(
        username: String,
        password: String,
        isUserDiscoverable: String
    ): Result<SlimeResponse<AuthResponse>>

    suspend fun checkAuthenticationState(): Result<SlimeResponse<Boolean>>
}
