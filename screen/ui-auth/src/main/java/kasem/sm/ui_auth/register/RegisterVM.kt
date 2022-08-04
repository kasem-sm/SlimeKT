/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kasem.sm.authentication.domain.interactors.RegisterService
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.AuthState
import kasem.sm.authentication.domain.model.Credentials
import kasem.sm.authentication.domain.usecases.ValidatePassword
import kasem.sm.authentication.domain.usecases.ValidateUsername
import kasem.sm.common_ui.R
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.utils.toMessage
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kasem.sm.ui_core.success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@HiltViewModel
class RegisterVM @Inject constructor(
    private val registerService: RegisterService,
    private val dispatchers: SlimeDispatchers,
    private val validateUsername: ValidateUsername,
    private val validatePassword: ValidatePassword,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val username = SavedMutableState(
        savedStateHandle,
        USERNAME_KEY,
        defValue = ""
    )

    private val usernameInfoMsg = SavedMutableState(
        savedStateHandle,
        USERNAME_ERR_MSG,
        defValue = ""
    )

    private val password = SavedMutableState(
        savedStateHandle,
        PASSWORD_KEY,
        defValue = ""
    )

    private val passwordInfoMsg = SavedMutableState(
        savedStateHandle,
        PASSWORD_ERR_MSG,
        defValue = ""
    )

    private val passwordVisibilityToggle = SavedMutableState(
        savedStateHandle,
        PASSWORD_VIS_KEY,
        defValue = true
    )

    private val loadingStatus = ObservableLoader()

    private val isAccountDiscoverable = SavedMutableState(
        savedStateHandle,
        DISCOVERABLE_TOGGLE_KEY,
        defValue = true
    )

    val state = combineFlows(
        username.flow,
        password.flow,
        loadingStatus.flow,
        passwordVisibilityToggle.flow,
        isAccountDiscoverable.flow,
        usernameInfoMsg.flow,
        passwordInfoMsg.flow
    ) { username, password, isLoading, passwordVisibility, isAccountDiscoverable, usernameErrMsg, passwordWarnMsg ->
        AuthState(
            username = username,
            password = password,
            isLoading = isLoading,
            passwordVisibility = passwordVisibility,
            isAccountDiscoverable = isAccountDiscoverable,
            usernameInfoMsg = usernameErrMsg,
            passwordInfoMsg = passwordWarnMsg
        )
    }.stateIn(
        coroutineScope = viewModelScope + dispatchers.main,
        initialValue = AuthState.EMPTY
    )

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onUsernameChange(updatedUsername: String) {
        // Because of recomposition this function may call with same value, that's why we need return here
        if (username.value == updatedUsername) return
        val validatedResult = validateUsername(updatedUsername)
        username.value = validatedResult.formattedValue
        usernameInfoMsg.value = validatedResult.message ?: ""
    }

    fun onPasswordChange(updatedPassword: String) {
        // Because of recomposition this function may call with same value, that's why we need return here
        if (password.value == updatedPassword) return
        val validatedResult = validatePassword(updatedPassword)
        password.value = validatedResult.formattedValue
        passwordInfoMsg.value = validatedResult.message ?: ""
    }

    fun togglePasswordVisibility(updatedValue: Boolean) {
        passwordVisibilityToggle.value = updatedValue
    }

    fun toggleAccountDiscoverability(updatedValue: Boolean) {
        isAccountDiscoverable.value = updatedValue
    }

    fun registerUser() {
        viewModelScope.launch(dispatchers.main) {
            val credentials = Credentials(
                username = username.value,
                password = password.value,
                isAccountDiscoverable = isAccountDiscoverable.value
            )
            registerService.execute(credentials)
                .onStart { loadingStatus.start() }
                .onCompletion { loadingStatus.stop() }
                .collectLatest { result ->
                    _uiEvent.emit(
                        when (result) {
                            is AuthResult.Exception -> showMessage(result.throwable.toMessage)
                            is AuthResult.EmptyCredentials -> showMessage(R.string.err_both_fields_empty)
                            is AuthResult.Success -> success()
                        }
                    )
                }
        }
    }

    fun clearInfoMsgsOfTextFields() {
        usernameInfoMsg.value = ""
        passwordInfoMsg.value = ""
    }

    companion object {
        const val USERNAME_KEY = "slime_reg_username"
        const val PASSWORD_KEY = "slime_reg_password"
        const val PASSWORD_VIS_KEY = "slime_reg_pass_vis_toggle"
        const val DISCOVERABLE_TOGGLE_KEY = "slime_dis_toggle"
        const val USERNAME_ERR_MSG = "slime_user_info_msg"
        const val PASSWORD_ERR_MSG = "slime_pw_info_msg"
    }
}
