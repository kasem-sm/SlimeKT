/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kasem.sm.auth_api.AuthManager
import kasem.sm.auth_api.AuthState
import kasem.sm.auth_api.ObserveAuthState
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val dispatchers: SlimeDispatchers,
    private val observeAuthState: ObserveAuthState,
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val isUserAuthenticated = MutableStateFlow(false)

    init {
        viewModelScope.launch(dispatchers.main) {
            observeAuthState.joinAndCollect(
                coroutineScope = viewModelScope
            ).collectLatest {
                isUserAuthenticated.value = it == AuthState.LOGGED_IN
            }
        }
    }

    fun clearUserSession() {
        viewModelScope.launch(dispatchers.main) {
            authManager.clearSession()
            _uiEvent.emit(success())
        }
    }
}
