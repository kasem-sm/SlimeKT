/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.io.IOException
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultQuadData
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultQuadDataWithOneFalse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockArticleResponse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockSuccessResponse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.mockSuccessResponseWithNullData
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.common_test_utils.shouldBeInOrder
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.Stage.Companion.exception
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLatestArticleTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val apiMock: ArticleApiService = mockk()

    private val useCase = GetArticles(
        api = apiMock,
        cache = databaseMock,
        dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher())
    )

    @Test
    fun assertApiCallErrorIsAnExceptionStage_and_InsertNotCalled() = runBlocking {
        coEvery { apiMock.getAllArticles() } returns Result.failure(IllegalCallerException())

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe IllegalCallerException()
        }

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultQuadData))
        }
    }

    @Test
    fun assertApiCallSuccess_and_InsertCalled() = runBlocking {
        coEvery {
            apiMock.getAllArticles()
        } returns mockSuccessResponse(data = mockArticleResponse())
        coEvery { databaseMock.getData(1) } returns defaultQuadDataWithOneFalse
        coEvery { databaseMock.insert(getMockEntity(defaultQuadDataWithOneFalse)) } just runs

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem() shouldBe Stage.Success
        }

        coVerify { databaseMock.insert(getMockEntity(defaultQuadDataWithOneFalse)) }
    }

    @Test
    fun assertApiCallSuccess_but_DataIsNull() = runBlocking {
        coEvery { apiMock.getAllArticles() } returns mockSuccessResponseWithNullData()

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem() shouldBe Stage.Success
        }

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultQuadData))
        }
    }

    @Test
    fun apiCallThrowsException() = runBlocking {
        coEvery { apiMock.getAllArticles() } throws IOException()

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe IOException()
        }
    }

    @Test
    fun cacheThrowsException() = runBlocking {
        coEvery { apiMock.getAllArticles() } returns mockSuccessResponse(mockArticleResponse())
        coEvery { databaseMock.getData(1) } throws UnknownError()

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe UnknownError()
        }
    }
}
