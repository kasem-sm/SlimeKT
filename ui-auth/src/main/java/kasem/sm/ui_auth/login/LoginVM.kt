/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.authentication.domain.interactors.LoginUseCase
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kasem.sm.common_ui.R
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.utils.toMessage
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel
class LoginVM @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatchers: SlimeDispatchers,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val username = SavedMutableState(
        savedStateHandle,
        USERNAME_KEY,
        defValue = ""
    )

    private val password = SavedMutableState(
        savedStateHandle,
        PASSWORD_KEY,
        defValue = ""
    )

    private val passwordVisibilityToggle = SavedMutableState(
        savedStateHandle,
        PASSWORD_VIS_KEY,
        defValue = true
    )

    private val loadingStatus = ObservableLoader()

    private val scope = viewModelScope + dispatchers.main

    val state = combineFlows(
        username.flow,
        password.flow,
        loadingStatus.flow,
        passwordVisibilityToggle.flow
    ) { username, password, isLoading, passwordVisibility ->
        AuthState(
            username = username,
            password = password,
            isLoading = isLoading,
            passwordVisibility = passwordVisibility
        )
    }.stateIn(
        coroutineScope = viewModelScope + dispatchers.main,
        initialValue = AuthState.EMPTY
    )

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onUsernameChange(updatedUsername: String) {
        username.value = updatedUsername
    }

    fun onPasswordChange(updatedPassword: String) {
        password.value = updatedPassword
    }

    fun togglePasswordVisibility(updatedValue: Boolean) {
        passwordVisibilityToggle.value = updatedValue
    }

    fun loginUser() {
        viewModelScope.launch(dispatchers.main) {
            val credentials = Credentials(
                username = username.value,
                password = password.value
            )
            loginUseCase.execute(credentials)
                .onStart { loadingStatus.start() }
                .onCompletion { loadingStatus.stop() }
                .collectLatest { result ->
                    _uiEvent.emit(
                        when (result) {
                            is AuthResult.Exception -> showMessage(result.throwable.toMessage)
                            is AuthResult.EmptyCredentials -> showMessage(R.string.err_both_fields_empty)
                            is AuthResult.Success -> UiEvent.Success
                        }
                    )
                }
        }
    }

    companion object {
        const val USERNAME_KEY = "slime_log_username"
        const val PASSWORD_KEY = "slime_log_password"
        const val PASSWORD_VIS_KEY = "slime_log_pass_vis_toggle"
    }
}
