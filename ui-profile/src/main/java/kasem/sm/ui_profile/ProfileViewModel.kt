/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.interfaces.Session
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.navigate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val session: Session,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun verifyAuthenticationStatus() {
        viewModelScope.launch {
            if (session.fetchToken() == null) {
                _uiEvent.emit(navigate(Routes.LoginScreen.route))
                return@launch
            }
        }
    }

    fun clearUserSession() {
        viewModelScope.launch {
            session.storeToken(null)
            _uiEvent.emit(navigate(Routes.LoginScreen.route))
        }
    }
}
