/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.interfaces

/**
 * This interface is responsible for fetching and
 * storing a user's authentication token.
 */
interface Session {
    suspend fun storeToken(token: String?,)
    fun fetchToken(): String?
}
