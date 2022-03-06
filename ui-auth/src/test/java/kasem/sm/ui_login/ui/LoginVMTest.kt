/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.authentication.domain.interactors.LoginUseCase
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.Credentials
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.common_ui.R.string
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_auth.login.LoginVM
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.UiText
import kasem.sm.ui_core.showMessage
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTime
class LoginVMTest {
    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private lateinit var viewModel: LoginVM

    private val loginUseCase: LoginUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = LoginVM(
            loginUseCase = loginUseCase,
            dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher()),
            savedStateHandle = SavedStateHandle()
        )
    }

    @Test
    fun testAuthStateChanges() = runTest {
        viewModel.onUsernameChange("usr")
        viewModel.onPasswordChange("pass")
        viewModel.togglePasswordVisibility(true)

        viewModel.state.test {
            val data = awaitItem()
            data shouldBe AuthState(
                username = "usr", password = "pass", passwordVisibility = true,
            )
        }
    }

    @Test
    fun testUiState_WhenLoginEmitsError() = runTest {
        coEvery {
            loginUseCase.execute(Credentials())
        } returns flow {
            emit(AuthResult.Exception(UnknownError()))
        }

        viewModel.uiEvent.test {
            viewModel.loginUser()
            val item = awaitItem()
            item shouldBe UiEvent.ShowMessage(UiText.StringText("Something went wrong!"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testUiEvent_WhenBothFieldsAreEmpty() = runTest {
        coEvery {
            loginUseCase.execute(Credentials())
        } returns flow {
            emit(AuthResult.EmptyCredentials(isUsernameEmpty = true, isPasswordEmpty = true))
        }

        viewModel.uiEvent.test {
            viewModel.loginUser()
            val item = awaitItem()
            item shouldBe showMessage(string.err_both_fields_empty)
            cancelAndConsumeRemainingEvents()
        }
    }
}
