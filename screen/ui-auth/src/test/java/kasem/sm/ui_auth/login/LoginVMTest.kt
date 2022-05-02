/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.login

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kasem.sm.authentication.domain.model.AuthResult
import kasem.sm.authentication.domain.model.AuthState
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.common_ui.R.string
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.ui_core.UiEvent
import kasem.sm.ui_core.UiText
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_core.success
import kotlin.time.ExperimentalTime
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

    private val fakeLoginUseCase = FakeLoginUseCase()

    @Before
    fun setUp() {
        viewModel = LoginVM(
            loginService = fakeLoginUseCase.mock,
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
        fakeLoginUseCase.mockAndReturn(AuthResult.Exception(UnknownError()))

        viewModel.uiEvent.test {
            viewModel.loginUser()
            val item = awaitItem()
            item shouldBe UiEvent.ShowMessage(UiText.StringText("Something went wrong!"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testUiEvent_WhenBothFieldsAreEmpty() = runTest {
        fakeLoginUseCase.mockAndReturn(
            AuthResult.EmptyCredentials(
                isUsernameEmpty = true,
                isPasswordEmpty = true
            )
        )

        viewModel.uiEvent.test {
            viewModel.loginUser()
            val item = awaitItem()
            item shouldBe showMessage(string.err_both_fields_empty)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testUiEvent_WhenLoginIsSuccess() = runTest {
        fakeLoginUseCase.mockAndReturn(AuthResult.Success)

        viewModel.uiEvent.test {
            viewModel.loginUser()
            val item = awaitItem()
            item shouldBe success()
            cancelAndConsumeRemainingEvents()
        }
    }
}
