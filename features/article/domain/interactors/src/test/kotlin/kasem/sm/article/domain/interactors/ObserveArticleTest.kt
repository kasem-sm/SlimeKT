/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import app.cash.turbine.test
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.interactors.utils.getMockEntity
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveArticleTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val mapper = ArticleMapper()

    private val observer = ObserveArticle(
        cache = databaseMock,
        mapper = mapper
    )

    @Test
    fun assertFlowEmitsProperValue() = runTest {
        coEvery { databaseMock.getArticleById(2) } returns flow { emit(getMockEntity().copy(id = 2)) }

        observer.joinAndCollect(params = 2, coroutineScope = TestScope()).test {
            Truth.assertThat(awaitItem()?.id).isEqualTo(2)
        }
    }

    @Test
    fun assertExceptionIsCaught() = runBlocking {
        val list = mutableListOf<String>()
        coEvery { databaseMock.getArticleById(2) } throws ArrayIndexOutOfBoundsException()

        observer.joinAndCollect(
            coroutineScope = TestScope(UnconfinedTestDispatcher()),
            onError = { list.add(it) },
            params = 2,
        ).test { cancelAndConsumeRemainingEvents() }

        list.first() shouldBe "Something went wrong!"
    }
}
