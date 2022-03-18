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
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultTriplets
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultTripletsWithOneFalse
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

    private val useCase = GetLatestArticles(
        api = apiMock,
        cache = databaseMock,
        dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher())
    )

    @Test
    fun assertApiCallErrorIsAnExceptionStage_and_InsertNotCalled() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } returns Result.failure(IllegalCallerException())

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe IllegalCallerException()
        }

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultTriplets))
        }
    }

    @Test
    fun assertApiCallSuccess_and_InsertCalled() = runBlocking {
        coEvery {
            apiMock.getAllArticles(
                page = 0,
                pageSize = 10
            )
        } returns mockSuccessResponse(data = mockArticleResponse())
        coEvery { databaseMock.getRespectiveTriplets(1) } returns defaultTripletsWithOneFalse
        coEvery { databaseMock.insert(getMockEntity(defaultTripletsWithOneFalse)) } just runs

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem() shouldBe Stage.Success
        }

        coVerify { databaseMock.insert(getMockEntity(defaultTripletsWithOneFalse)) }
    }

    @Test
    fun assertApiCallSuccess_but_DataIsNull() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } returns mockSuccessResponseWithNullData()

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem() shouldBe Stage.Success
        }

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultTriplets))
        }
    }

    @Test
    fun apiCallThrowsException() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } throws IOException()

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe IOException()
        }
    }

    @Test
    fun cacheThrowsException() = runBlocking {
        coEvery { apiMock.getAllArticles(0, 10) } returns mockSuccessResponse(mockArticleResponse())
        coEvery { databaseMock.getRespectiveTriplets(1) } throws UnknownError()

        useCase.execute().shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe UnknownError()
        }
    }
}
