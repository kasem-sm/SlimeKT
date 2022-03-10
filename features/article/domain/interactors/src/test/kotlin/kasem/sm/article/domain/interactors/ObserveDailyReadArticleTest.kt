/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import java.sql.SQLException
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultTripletsWithOneFalse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
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
class ObserveDailyReadArticleTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val mapper = ArticleMapper()

    private val observer = ObserveDailyReadArticle(
        cache = databaseMock,
        mapper = mapper
    )

    @Test
    fun assertFlowEmitsProperValue() = runTest {
        coEvery { databaseMock.getActiveArticleFlow() } returns flow {
            emit(getMockEntity(defaultTripletsWithOneFalse))
        }

        observer.joinAndCollect(
            params = Unit,
            coroutineScope = TestScope()
        ).test {
            awaitItem()?.isActiveInDailyRead shouldBe defaultTripletsWithOneFalse.second.isActive
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun assertExceptionIsCaught() = runBlocking {
        val list = mutableListOf<String>()
        coEvery { databaseMock.getActiveArticleFlow() } throws SQLException("Table doesn't exists")

        observer.joinAndCollect(
            params = Unit,
            coroutineScope = TestScope(UnconfinedTestDispatcher()),
            onError = { list.add(it) },
        ).test { cancelAndIgnoreRemainingEvents() }

        list.first() shouldBe "Table doesn't exists"
    }
}
