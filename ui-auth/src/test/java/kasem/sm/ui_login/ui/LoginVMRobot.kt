/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kasem.sm.core.domain.Dispatchers
import kasem.sm.ui_auth.login.LoginVM
import kasem.sm.ui_login.data.FakeLoginUseCase
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalTime
class LoginVMRobot {
    private val fakeCredentialsLoginUseCase = FakeLoginUseCase()
    private lateinit var viewModel: LoginVM

    fun buildViewModel() = apply {
        viewModel = LoginVM(
            loginUseCase = fakeCredentialsLoginUseCase.mock,
            dispatchers = testDispatchers,
            savedStateHandle = SavedStateHandle()
        )
    }

    fun mockLoginResult(
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
     * Launch a coroutine that will observe our [LoginVM]'s viewState
     */

    @Suppress("Unused_Expression")
    suspend fun <T> test(
        action: suspend LoginVMRobot.() -> Unit,
        expectedResult: T,
        flow: Flow<T>
    ) {
        action()
        flow.test {
            assertThat(awaitItem()).isEqualTo(expectedResult)
            cancelAndConsumeRemainingEvents()
        }
    }

    companion object {
        val testDispatchers get() = StandardTestDispatcher().run {
            Dispatchers(this, this, this)
        }
    }
}
