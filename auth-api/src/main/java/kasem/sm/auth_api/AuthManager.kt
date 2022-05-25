/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.auth_api

import kotlinx.coroutines.flow.StateFlow

interface AuthManager {
    val state: StateFlow<AuthState>
    fun onNewSession(session: SlimeSession)
    fun getUserData(data: UserData): String?
    fun clearSession()

    data class SlimeSession(val token: String, val userId: String)
}
