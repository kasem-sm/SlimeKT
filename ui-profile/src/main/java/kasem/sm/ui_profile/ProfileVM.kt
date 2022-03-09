/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.interfaces.AuthManager
import kasem.sm.core.session.AuthState
import kasem.sm.core.session.ObserveAuthState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

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
        observeAuthState.join(viewModelScope)

        viewModelScope.launch(dispatchers.main) {
            observeAuthState.flow.collect {
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
