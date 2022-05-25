/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import kasem.sm.article.domain.model.Article
import kasem.sm.topic.domain.model.Topic
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun assertTopicSubscribeCard_isShown_when_subscriptionList_isEmpty() {
        composeTestRule.setContent {
            homeContentForTest(
                state = HomeState(
                    topics = emptyList(),
                    isLoading = false,
                )
            )
        }

        composeTestRule
            .onNodeWithTag("subscription_card")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("topics_item_view")
            .assertDoesNotExist()
    }

    @Test
    fun assertTopicSubscribeCard_isShown_when_userIsNotAuthenticated() {
        composeTestRule.setContent {
            // No matter if topics in db exists,
            // if user is not authenticated, we should not display the
            // topics lazy row
            homeContentForTest(
                state = HomeState(
                    topics = listOf(getMockTopic()),
                    isLoading = false,
                    isUserAuthenticated = false
                )
            )
        }

        composeTestRule
            .onNodeWithTag("subscription_card")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("topics_item_view")
            .assertDoesNotExist()
    }

    @Test
    fun assertTopicSubscribeCard_isNotShown_when_userIsAuthenticated_andTopicsListIsNotEmpty() {
        composeTestRule.setContent {
            // No matter if topics in db exists,
            // if user is not authenticated, we should not display the
            // topics lazy row
            homeContentForTest(
                state = HomeState(
                    topics = listOf(getMockTopic()),
                    isLoading = false,
                    isUserAuthenticated = true
                )
            )
        }

        composeTestRule
            .onNodeWithTag("subscription_card")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag("topics_item_view")
            .assertIsDisplayed()
    }

    @Test
    fun assertTopicSubscribeCard_isShown_when_userIsAuthenticated_andTopicsListIsEmpty() {
        composeTestRule.setContent {
            // No matter if topics in db exists,
            // if user is not authenticated, we should not display the
            // topics lazy row
            homeContentForTest(
                state = HomeState(
                    topics = emptyList(),
                    isLoading = false,
                    isUserAuthenticated = true
                )
            )
        }

        composeTestRule
            .onNodeWithTag("subscription_card")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("topics_item_view")
            .assertDoesNotExist()
    }

    @Test
    fun assertDailyReadArticleView_IsNotDisplayed_if_isNull() {
        composeTestRule.setContent {
            homeContentForTest(
                state = HomeState(
                    dailyReadArticle = null,
                )
            )
        }

        composeTestRule
            .onNodeWithTag("daily_read_article_view")
            .assertDoesNotExist()
    }

    @Test
    fun testBookmarkIcon() {
        composeTestRule.setContent {
            homeContentForTest(
                state = HomeState(
                    articles = listOf(
                        getMockDomain(isBookmarked = true),
                        getMockDomain(isBookmarked = false),
                        getMockDomain(isBookmarked = true)
                    )
                )
            )
        }

        composeTestRule
            .onAllNodesWithTag("article_isBookmarked")
            .assertCountEquals((2))

        composeTestRule
            .onAllNodesWithTag("article_isNotBookmarked")
            .assertCountEquals((1))
    }

    @Test
    fun assert_topicChip_isSelected_ifQueryMatches_topicTitle() {
        composeTestRule.setContent {
            homeContentForTest(
                state = HomeState(
                    isLoading = false,
                    isUserAuthenticated = true,
                    currentQuery = "Dummy Title",
                    topics = listOf(getMockTopic().copy(title = "Dummy Title")),
                ),
            )
        }

        composeTestRule
            .onNodeWithTag("topic_chip")
            .assertBackgroundColor("sRGB IEC61966-2.1")
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

fun getMockDomain(isBookmarked: Boolean): Article {
    return Article(
        id = 1,
        title = "title",
        description = "description",
        featuredImage = "img",
        author = "author",
        timestamp = 1L,
        hasUserSeen = true,
        isShownInDailyRead = true,
        isActiveInDailyRead = true,
        isInExplore = true,
        topic = "topic",
        isInBookmark = isBookmarked
    )
}

// https://stackoverflow.com/questions/70682864/android-jetpack-compose-how-to-test-background-color
fun SemanticsNodeInteraction.assertBackgroundColor(colorSpaceName: String) {
    val capturedName = captureToImage().colorSpace.name
    assertEquals(colorSpaceName, capturedName)
}
