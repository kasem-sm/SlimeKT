/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.common_ui.util.TestTags
import kasem.sm.ui_auth.common.AuthState
import kasem.sm.ui_auth.login.LoginContent
import org.junit.Rule
import org.junit.Test

class LoginContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clickLoginButton() {
        var hasUserClicked = false

        composeTestRule.setContent {
            LoginContent(
                state = AuthState(),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = {
                    hasUserClicked = true
                },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithText("Continue")
            .performClick()

        hasUserClicked shouldBe true
    }

    @Test
    fun assertStateChanges() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(username = "usr"),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.USERNAME_FIELD)
            .assert(hasText("usr"))
    }

    @Test
    fun assertPasswordIsNotShown_When_VisibilityToggle_IsDisabled() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(password = "123", passwordVisibility = false),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.PASSWORD_FIELD)
            .assert(hasText("•••"))
    }

    @Test
    fun assertPasswordIsShown_When_VisibilityToggle_IsEnabled() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(password = "123", passwordVisibility = true),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.PASSWORD_FIELD)
            .assert(hasText("123"))
    }

    @Test
    fun assertProgressBarIsShown_When_IsLoading() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(isLoading = true),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.BUTTON_PROGRESS_BAR)
            .assertIsDisplayed()
    }

    @Test
    fun assertLoginButtonTextChanges_When_IsLoading() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(isLoading = true),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.LOGIN_BUTTON)
            .assert(hasText("Please wait"))
    }

    @Test
    fun assertProgressBarIsNotShown_When_IsNotLoading() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(isLoading = false),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.BUTTON_PROGRESS_BAR)
            .assertDoesNotExist()
    }

    @Test
    fun testUsernameFieldIMEAction_Should_MoveFocusToPasswordField() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(username = "usr", password = "123"),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.USERNAME_FIELD)
            .performImeAction()

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.PASSWORD_FIELD)
            .assertIsFocused()
    }

    @Test
    fun testPasswordFieldIMEAction_Should_Login() {
        var onLoginClicked = false
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(username = "user", password = "1234"),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { onLoginClicked = true },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.PASSWORD_FIELD, useUnmergedTree = true)
            .performImeAction()

        onLoginClicked shouldBe true
    }

    @Test
    fun assertButtonsAreDisabled_While_isLoadingIsTrue() {
        composeTestRule.setContent {
            LoginContent(
                state = AuthState(isLoading = true),
                onUsernameChanged = { },
                onPasswordChanged = { },
                onLoginClicked = { },
                onPasswordToggleClicked = { },
                onSignUpClicked = { }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.LOGIN_BUTTON)
            .assert(!isEnabled())

        composeTestRule
            .onNodeWithTag(TestTags.LoginContent.SIGN_IN_BUTTON)
            .assert(hasNoClickAction())
    }
}
