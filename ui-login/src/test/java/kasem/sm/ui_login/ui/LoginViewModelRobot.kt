/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kasem.sm.core.interfaces.Session
import kasem.sm.feature_auth.domain.model.AuthResult
import kasem.sm.feature_auth.domain.model.Credentials
import kasem.sm.ui_login.LoginViewModel
import kasem.sm.ui_login.data.FakeLoginUseCase
import kasem.sm.ui_login.utils.testDispatchers
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow

@ExperimentalTime
class LoginViewModelRobot {
    private val fakeCredentialsLoginUseCase = FakeLoginUseCase()
    private val fakeSessionRepository: Session = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    fun buildViewModel() = apply {
        viewModel = LoginViewModel(
            loginUseCase = fakeCredentialsLoginUseCase.mock,
            session = fakeSessionRepository,
            slimeDispatchers = testDispatchers(),
            savedStateHandle = SavedStateHandle()
        )
    }

    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: AuthResult,
    ) = apply {
        fakeCredentialsLoginUseCase.mockLoginResultForCredentials(
            credentials = credentials,
            result = result
        )
    }

    fun enterUsername(username: String) = apply {
        viewModel.onUsernameChange(username)
    }

    fun enterPassword(password: String) = apply {
        viewModel.onPasswordChange(password)
    }

    fun clickLoginButton() = apply {
        viewModel.loginUser()
    }

    val actual get() = viewModel

    /**
     * Launch a coroutine that will observe our [LoginViewModel]'s viewState
     */

    suspend fun <T> test(
        action: suspend LoginViewModelRobot.() -> Unit,
        expectedResult: T,
        flow: Flow<T>
    ) {
        action()
        flow.test {
            assertThat(awaitItem()).isEqualTo(expectedResult)
            cancelAndConsumeRemainingEvents()
        }
    }
}
