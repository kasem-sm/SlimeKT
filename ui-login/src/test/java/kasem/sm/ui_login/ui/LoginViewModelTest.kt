/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login.ui

import kasem.sm.ui_login.LoginViewState
import kasem.sm.ui_login.utils.CoroutinesTestRule
import kasem.sm.ui_login.utils.ThreadExceptionTestRule
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTime
class LoginViewModelTest {
    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var viewModelRobot: LoginViewModelRobot

    @Before
    fun setUp() {
        viewModelRobot = LoginViewModelRobot()
    }

    @Test
    fun testIfCredentialsUpdate() = runTest {
        viewModelRobot
            .buildViewModel()
            .test(
                action = {
                    enterUsername("usr")
                },
                flow = viewModelRobot.actual.username.flow,
                expectedResult = "usr",
            )
    }

    @Test
    fun testStateChanges() = runTest {
        viewModelRobot
            .buildViewModel()
            .test(
                action = {
                    enterUsername("usr")
                    enterPassword("pass")
                },
                flow = viewModelRobot.actual.state,
                expectedResult = LoginViewState(
                    username = "usr", password = "pass",
                ),
            )
    }
}
