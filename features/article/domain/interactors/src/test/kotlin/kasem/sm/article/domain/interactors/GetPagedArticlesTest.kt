/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockArticleResponse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockSuccessResponse
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.core.domain.PaginationOver
import kasem.sm.core.domain.PaginationStage
import kasem.sm.core.domain.SlimeDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPagedArticlesTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val apiMock: ArticleApiService = mockk()

    private val mapper = ArticleMapper()

    private val useCase = GetPagedArticles(
        api = apiMock,
        cache = databaseMock,
        applicationScope = TestScope(UnconfinedTestDispatcher()),
        dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher()),
        mapper = mapper
    )

    @Test
    fun assertResultIsPaginationOver_WhenAppropriateExceptionIsThrown() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } returns Result.failure(PaginationOver())

        val data = useCase.execute(
            page = 0,
            pageSize = 10,
            topic = "",
            query = ""
        ).toList().first()

        Truth.assertThat(data).isEqualTo(PaginationStage.PaginationOver)
    }

    @Test
    fun assertApiCallSuccess_and_DataEmittedSuccessfully() = runBlocking {
        coEvery {
            apiMock.getAllArticles(page = 0, pageSize = 10, topic = "", query = "")
        } returns mockSuccessResponse(mockArticleResponse())

        coEvery {
            databaseMock.getPagedArticles(
                page = 1,
                pageSize = 10,
                topic = "",
                query = ""
            )
        } returns listOf(getMockEntity())

        val data = useCase.execute(
            page = 0,
            pageSize = 10,
            topic = "",
            query = ""
        ).toList().first()

        Truth.assertThat(data).isEqualTo(
            PaginationStage.Success(
                mapper.map(listOf(getMockEntity()))
            )
        )
    }

    /**
     * Scenario - Requested for 3 pages from API but in real only 2 pages existed (totalPage in InfoDto),
     * and the cache tried to request page number 3 so PaginationOver will be thrown
     * as page requested from cache exceeded totalPage Number in Info Dto. (2 > 3)
     */
    @Test
    fun infoDtoPageIsLessThan_ThePageRequestedFromCache_AssertPaginationOver() = runBlocking {
        coEvery {
            // Requested for 3 Pages from API
            apiMock.getAllArticles(page = 3, pageSize = 10)
            // API responded with only 2 pages
        } returns mockSuccessResponse(mockArticleResponse(totalPage = 2))

        // We tried to fetch page number 3 that never existed
        val data = useCase.execute(
            page = 3,
            pageSize = 10,
            topic = "",
            query = ""
        ).toList().first()

        // Pagination over is thrown
        Truth.assertThat(data).isEqualTo(PaginationStage.PaginationOver)
    }
}