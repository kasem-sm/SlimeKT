/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import java.sql.SQLTimeoutException
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultTripletsWithOneFalse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
import kasem.sm.article.domain.interactors.utils.ArticleFakes.toDomain
import kasem.sm.article.domain.observers.ObserveLatestArticles
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveLatestArticlesTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val mapper = ArticleMapper()

    private val observer = ObserveLatestArticles(
        cache = databaseMock,
        mapper = mapper
    )

    @Test
    fun assertFlowEmitsProperValue() = runTest {
        coEvery {
            databaseMock.getPagedArticles(
                page = any(), pageSize = any()
            )
        } returns listOf(getMockEntity(), getMockEntity(defaultTripletsWithOneFalse).copy(id = 2))

        observer.joinAndCollect(
            params = Unit,
            coroutineScope = TestScope()
        ).test {
            awaitItem() shouldBe listOf(
                getMockEntity().toDomain(),
                getMockEntity(defaultTripletsWithOneFalse).copy(id = 2).toDomain(defaultTripletsWithOneFalse)
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun assertExceptionIsCaught() = runBlocking {
        val list = mutableListOf<String>()
        coEvery {
            databaseMock.getPagedArticles(
                page = any(),
                pageSize = any()
            )
        } throws SQLTimeoutException("Exceeds")

        observer.joinAndCollect(
            coroutineScope = TestScope(UnconfinedTestDispatcher()),
            onError = { list.add(it) },
            params = Unit,
        ).test { cancelAndIgnoreRemainingEvents() }

        list.first() shouldBe "Exceeds"
    }
}
