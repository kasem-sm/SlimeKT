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
import kasem.sm.common_ui.util.Routes
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.ObservableLoader.Companion.Loader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.interfaces.Session
import kasem.sm.core.utils.toMessage
import kasem.sm.feature_auth.domain.interactors.LoginUseCase
import kasem.sm.feature_auth.domain.model.AuthResult
import kasem.sm.feature_auth.domain.model.Credentials
import kasem.sm.ui_auth.common.AuthViewState
import kasem.sm.ui_core.SavedMutableState
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.combineFlows
import kasem.sm.ui_core.navigate
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val slimeDispatchers: SlimeDispatchers,
    private val session: Session,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val username = SavedMutableState(
        savedStateHandle,
        "slime_username",
        defaultValue = ""
    )

    private val password = SavedMutableState(
        savedStateHandle,
        "slime_password",
        defaultValue = ""
    )

    private val passwordVisibilityToggle = SavedMutableState(
        savedStateHandle,
        "slime_pass_vis_toggle",
        defaultValue = true
    )

    private val loadingStatus = ObservableLoader()

    val state = combineFlows(
        username.flow,
        password.flow,
        loadingStatus.flow,
        passwordVisibilityToggle.flow
    ) { username, password, isLoading, passwordVisibility ->
        AuthViewState(
            username = username,
            password = password,
            isLoading = isLoading,
            passwordVisibility = passwordVisibility
        )
    }.stateIn(viewModelScope, AuthViewState.EMPTY)

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun checkAuthenticationStatus() {
        val userToken = session.fetchToken()
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            if (userToken != null) {
                _uiEvent.emit(navigate(Routes.HomeScreen.route))
            }
        }
    }

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
        viewModelScope.launch(slimeDispatchers.mainDispatcher) {
            val credentials = Credentials(
                username = username.value,
                password = password.value
            )
            loginUseCase.execute(credentials)
                .onStart { loadingStatus(Loader.START) }
                .onCompletion { loadingStatus(Loader.STOP) }
                .collectLatest { result ->
                    _uiEvent.emit(
                        when (result) {
                            is AuthResult.Exception -> showMessage(result.throwable.toMessage)
                            is AuthResult.EmptyCredentials -> showMessage("Please enter your username and password")
                            is AuthResult.Success -> navigate(Routes.SubscribeCategoryScreen.route)
                        }
                    )
                }
        }
    }
}
