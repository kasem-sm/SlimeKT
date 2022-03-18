/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
import kasem.sm.article.domain.observers.ObserveArticle
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

        observer.joinAndCollect(
            params = 2,
            coroutineScope = TestScope()
        ).test {
            awaitItem()?.id shouldBe 2
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun assertExceptionIsCaught() = runBlocking {
        val list = mutableListOf<String>()
        coEvery { databaseMock.getArticleById(2) } throws
            ArrayIndexOutOfBoundsException("Please check this error message")

        observer.joinAndCollect(
            coroutineScope = TestScope(UnconfinedTestDispatcher()),
            onError = { list.add(it) },
            params = 2,
        ).test { cancelAndIgnoreRemainingEvents() }

        list.first() shouldBe "Please check this error message"
    }
}
