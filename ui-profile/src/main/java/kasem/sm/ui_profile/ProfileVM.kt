/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.core.interfaces.Session
import kasem.sm.ui_core.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileVM @Inject constructor(
    private val session: Session,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val isUserAuthenticated = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            session.observeAuthenticationState().collectLatest {
                isUserAuthenticated.value = it
            }
        }
    }

    fun clearUserSession() {
        viewModelScope.launch {
            session.clear()
            _uiEvent.emit(UiEvent.Success)
        }
    }
}
