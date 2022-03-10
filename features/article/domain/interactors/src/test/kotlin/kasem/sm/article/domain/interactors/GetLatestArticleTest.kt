/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.IOException
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultTriplets
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultTripletsWithOneFalse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockArticleResponse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockSuccessResponse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockSuccessResponseWithNullData
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLatestArticleTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val apiMock: ArticleApiService = mockk()

    private val useCase = GetLatestArticles(
        api = apiMock,
        cache = databaseMock,
        applicationScope = TestScope(UnconfinedTestDispatcher()),
        dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher())
    )

    @Test
    fun assertApiCallErrorIsAnExceptionStage_and_InsertNotCalled() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } returns Result.failure(IllegalCallerException())

        val stage = useCase.execute().first()

        (stage as Stage.Exception).throwable shouldBe IllegalCallerException()

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultTriplets))
        }
    }

    @Test
    fun assertApiCallSuccess_and_InsertCalled() = runBlocking {
        coEvery {
            apiMock.getAllArticles(
                0,
                10
            )
        } returns mockSuccessResponse(data = mockArticleResponse())
        coEvery { databaseMock.getRespectiveTriplets(1) } returns defaultTripletsWithOneFalse

        val stage = useCase.execute().first()

        stage shouldBe Stage.Success

        coVerify { databaseMock.insert(getMockEntity(defaultTripletsWithOneFalse)) }
    }

    @Test
    fun assertApiCallSuccess_but_DataIsNull() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } returns mockSuccessResponseWithNullData()

        val stage = useCase.execute().first()

        stage shouldBe Stage.Success

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultTriplets))
        }
    }

    @Test
    fun apiCallThrowsException() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } throws IOException()

        val stage = useCase.execute().first()

        (stage as Stage.Exception).throwable shouldBe IOException()
    }

//    @Test
//    fun cacheThrowsException() = runBlocking {
//        coEvery { apiMock.getAllArticles(0, 10) } returns mockSuccessResponse(mockArticleResponse())
//        coEvery { databaseMock.getRespectivePair(1) } throws UnknownError()
//
//        val stage = useCase.execute().first()
//
//        stage shouldBe Stage.Exception()
//    }
}
