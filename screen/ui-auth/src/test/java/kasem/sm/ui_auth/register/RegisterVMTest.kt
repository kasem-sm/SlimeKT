/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register

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
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class RegisterVMTest {
    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private lateinit var viewModel: RegisterVM

    private val fakeRegisterUseCase = FakeRegisterUseCase()

    @Before
    fun setUp() {
        viewModel = RegisterVM(
            registerService = fakeRegisterUseCase.mock,
            dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher()),
            savedStateHandle = SavedStateHandle()
        )
    }

    @Test
    fun testAuthStateChanges() = runTest {
        viewModel.onUsernameChange("usr")
        viewModel.onPasswordChange("pass")
        viewModel.togglePasswordVisibility(true)
        viewModel.toggleAccountDiscoverability(false)

        viewModel.state.test {
            val data = awaitItem()
            data shouldBe AuthState(
                username = "usr",
                password = "pass",
                passwordVisibility = true,
                isAccountDiscoverable = false
            )
        }
    }

    @Test
    fun testUiState_WhenRegistrationEmitsError() = runTest {
        fakeRegisterUseCase.mockAndReturn(AuthResult.Exception(UnknownError()))

        viewModel.uiEvent.test {
            viewModel.registerUser()
            val item = awaitItem()
            item shouldBe UiEvent.ShowMessage(UiText.StringText("Something went wrong!"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testUiEvent_WhenBothFieldsAreEmpty() = runTest {
        fakeRegisterUseCase.mockAndReturn(
            AuthResult.EmptyCredentials(
                isUsernameEmpty = true,
                isPasswordEmpty = true
            )
        )

        viewModel.uiEvent.test {
            viewModel.registerUser()
            val item = awaitItem()
            item shouldBe showMessage(string.err_both_fields_empty)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testUiEvent_WhenRegistrationIsSuccess() = runTest {
        fakeRegisterUseCase.mockAndReturn(AuthResult.Success)

        viewModel.uiEvent.test {
            viewModel.registerUser()
            val item = awaitItem()
            item shouldBe success()
            cancelAndConsumeRemainingEvents()
        }
    }
}
