/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.interfaces

import kotlinx.coroutines.flow.Flow

/**
 * This interface is responsible for fetching and
 * storing a user's authentication token.
 */
interface Session {
    fun getUserToken(): String?
    fun getUserId(): String?
    suspend fun storeUserToken(token: String?)
    suspend fun storeUserId(id: String?)
    fun observeAuthenticationState(): Flow<Boolean>
    suspend fun clear()
}
