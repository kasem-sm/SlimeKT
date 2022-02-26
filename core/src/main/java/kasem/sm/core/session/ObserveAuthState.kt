/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.session

import javax.inject.Inject
import kasem.sm.core.domain.ObserverInteractor
import kasem.sm.core.interfaces.AuthManager
import kotlinx.coroutines.flow.Flow

class ObserveAuthState @Inject constructor(
    private val authManager: AuthManager
) : ObserverInteractor<Unit, AuthState>() {
    override suspend fun execute(params: Unit): Flow<AuthState> {
        return authManager.state
    }
}
