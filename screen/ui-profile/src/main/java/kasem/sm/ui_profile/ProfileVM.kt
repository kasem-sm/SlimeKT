/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slime.auth_api.AuthManager
import com.slime.auth_api.AuthState
import com.slime.auth_api.ObserveAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
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
