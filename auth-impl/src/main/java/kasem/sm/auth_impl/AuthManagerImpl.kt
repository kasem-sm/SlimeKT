/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.auth_impl

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import kasem.sm.auth_api.AuthManager
import kasem.sm.auth_api.AuthState
import kasem.sm.auth_api.ID
import kasem.sm.auth_api.Token
import kasem.sm.auth_api.UserData
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.task_api.Tasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthManagerImpl @Inject constructor(
    private val dispatchers: SlimeDispatchers,
    private val tasks: Tasks,
    private val preferences: SharedPreferences,
) : AuthManager {

    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.LOGGED_OUT)
    override val state: StateFlow<AuthState> = _state.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + dispatchers.default)

    init {
        scope.launch(dispatchers.io) {
            preferences.observe(AUTH_TOKEN_KEY, defValue = null).collectLatest {
                updateState(!it.isNullOrEmpty())
            }
        }
    }

    override fun onNewSession(session: AuthManager.SlimeSession) {
        scope.launch(dispatchers.io) {
            saveAuthState(session)
            tasks.executeDailyReader()

            withContext(dispatchers.main) {
                _state.value = AuthState.LOGGED_IN
            }
        }
    }

    override fun clearSession() {
        preferences.edit(commit = true) {
            putString(AUTH_TOKEN_KEY, null)
            putString(AUTH_ID_KEY, null)
        }
        scope.launch(dispatchers.io) {
            tasks.clearUserSubscriptionLocally()
        }
    }

    override fun getUserData(data: UserData): String? {
        return when (data) {
            is ID -> preferences.getString(AUTH_ID_KEY, null)
            is Token -> preferences.getString(AUTH_TOKEN_KEY, null)
            else -> null
        }
    }

    private fun saveAuthState(session: AuthManager.SlimeSession) {
        preferences.edit(commit = true) {
            putString(AUTH_TOKEN_KEY, session.token)
            putString(AUTH_ID_KEY, session.userId)
        }
    }

    private fun updateState(value: Boolean) {
        when (value) {
            true -> _state.value = AuthState.LOGGED_IN
            false -> _state.value = AuthState.LOGGED_OUT
        }
    }

    companion object {
        const val AUTH_TOKEN_KEY = "kasem.sm.slime.user_auth_token"
        const val AUTH_ID_KEY = "kasem.sm.slime.user_id"
    }
}
