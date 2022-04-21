/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import coil.ImageLoader
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.common_ui.drawableId
import kasem.sm.topic.domain.model.Topic
import kasem.sm.ui_article_list.components.SubscribeView
import org.junit.Rule
import org.junit.Test

@SuppressLint("ComposableNaming")
@Composable
fun Context.listContentForTest(
    state: ListState = ListState.EMPTY
) {
    ListContent(
        state = state,
        imageLoader = ImageLoader(this),
        onRefresh = { },
        onArticleClick = { },
        updateSubscription = { },
        showAuthenticationSheet = { },
        onBookmarkClick = { },
        listState = rememberLazyListState()
    )
}

class ListContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun assertSubscribeView_IsInvisible_When_TopicIsNull(): Unit = composeTestRule.run {
        setContent {
            activity.listContentForTest(state = ListState(topic = null))
        }

        onNodeWithTag("subscribeButtonAndHeader")
            .assertDoesNotExist()
    }

    @Test
    fun assertEmptyView_IsVisible_OnlyWhen_CertainConditionsMeet() = composeTestRule.run {
        setContent {
            activity.listContentForTest(
                state = ListState(
                    isLoading = false,
                    articles = emptyList()
                )
            )
        }

        onNodeWithTag("emptyView")
            .assertIsDisplayed()
        Unit
    }

    @Test
    fun assertEmptyView_IsNotVisible_WhenConditionsDoesNotMeet() = composeTestRule.run {
        setContent {
            activity.listContentForTest(
                state = ListState(
                    isLoading = false,
                    articles = emptyList()
                )
            )
        }
        onNodeWithTag("emptyView")
            .assertDoesNotExist()
    }

    @Test
    fun assertClickingSubscribeButton_OpensAuthenticationSheet_WhenUserIsNotAuthenticated() =
        composeTestRule.run {
            var updateSubscriptionClicked = false
            var showAuthenticationSheetClicked = false

            setContent {
                SubscribeView(
                    state = ListState(isUserAuthenticated = false, topic = getMockTopic()),
                    updateSubscription = {
                        updateSubscriptionClicked = true
                    },
                    showAuthenticationSheet = {
                        showAuthenticationSheetClicked = true
                    }
                )
            }

            composeTestRule
                .onNodeWithTag("subscribeButton")
                .performClick()

            updateSubscriptionClicked shouldBe false
            showAuthenticationSheetClicked shouldBe true
        }

    @Test
    fun assertClickingSubscribeButton_Subscribes_WhenUserIsAuthenticated() =
        composeTestRule.run {
            var updateSubscriptionClicked = false
            var showAuthenticationSheetClicked = false

            setContent {
                SubscribeView(
                    state = ListState(isUserAuthenticated = true, topic = getMockTopic()),
                    updateSubscription = {
                        updateSubscriptionClicked = true
                    },
                    showAuthenticationSheet = {
                        showAuthenticationSheetClicked = true
                    }
                )
            }

            composeTestRule
                .onNodeWithTag("subscribeButton")
                .performClick()

            updateSubscriptionClicked shouldBe true
            showAuthenticationSheetClicked shouldBe false
        }

    @Test
    fun assertSubscribeButton_IconAndText_WhenUserIsSubscribed() = composeTestRule.run {
        setContent {
            SubscribeView(
                state = ListState(
                    isUserAuthenticated = true,
                    topic = getMockTopic().copy(hasUserSubscribed = true)
                ),
                updateSubscription = {},
                showAuthenticationSheet = {}
            )
        }

        composeTestRule
            .onNodeWithTag("subscribeButton")
            .assertTextEquals("Unsubscribe")
            .assert(hasDrawable(R.drawable.ic_unsubscribe))
        Unit
    }

    @Test
    fun assertSubscribeButton_IconAndText_WhenUserIsUnSubscribed() = composeTestRule.run {
        setContent {
            SubscribeView(
                state = ListState(
                    isUserAuthenticated = true,
                    topic = getMockTopic().copy(hasUserSubscribed = false)
                ),
                updateSubscription = {},
                showAuthenticationSheet = {}
            )
        }

        composeTestRule
            .onNodeWithTag("subscribeButton")
            .assertTextEquals("Subscribe")
            .assert(hasDrawable(R.drawable.ic_subscribe))
        Unit
    }
}

fun getMockTopic(): Topic {
    return Topic(
        id = "1",
        title = "Topic",
        timestamp = 1L,
        isSelected = false,
        totalSubscribers = 1,
        hasUserSubscribed = false
    )
}

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(drawableId, id)
