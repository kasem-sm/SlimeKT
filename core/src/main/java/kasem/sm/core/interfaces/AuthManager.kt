/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.interfaces

import kasem.sm.core.session.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthManager {
    fun onNewSession(session: SlimeSession)
    fun clearSession()
    fun getUserId(): String?
    fun getUserToken(): String?
    val state: StateFlow<AuthState>

    data class SlimeSession(val token: String, val userId: String)
}
