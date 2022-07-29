/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.auth_api

import kasem.sm.core.domain.ObserverInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthState @Inject constructor(
    private val authManager: AuthManager
) : ObserverInteractor<Unit, AuthState>() {
    override fun execute(params: Unit): Flow<AuthState> {
        return authManager.state
    }
}
