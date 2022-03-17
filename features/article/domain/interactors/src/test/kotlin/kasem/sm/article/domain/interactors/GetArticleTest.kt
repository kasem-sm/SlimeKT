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
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockDto
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockEntity
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
class GetArticleTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val databaseMock: ArticleDatabaseService = mockk()
    private val apiMock: ArticleApiService = mockk()

    private val useCase = GetArticle(
        api = apiMock,
        cache = databaseMock,
        dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher())
    )

    @Test
    fun assertApiCallErrorIsAnExceptionStage_and_InsertNotCalled() = runBlocking {
        coEvery { apiMock.getArticleById(1) } returns Result.failure(RuntimeException())

        useCase.execute(1).shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe RuntimeException()
        }

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultTriplets))
        }
    }

    @Test
    fun assertApiCallSuccess_and_InsertCalled() = runBlocking {
        coEvery { apiMock.getArticleById(1) } returns mockSuccessResponse(data = getMockDto())
        coEvery { databaseMock.getRespectiveTriplets(1) } returns defaultTriplets
        coEvery { databaseMock.insert(getMockEntity(defaultTriplets)) } just runs

        useCase.execute(1).shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem() shouldBe Stage.Success
        }

        coVerify { databaseMock.insert(getMockEntity(defaultTriplets)) }
    }

    @Test
    fun assertApiCallSuccess_but_DataIsNull() = runBlocking {
        coEvery { apiMock.getArticleById(1) } returns mockSuccessResponseWithNullData()

        useCase.execute(1).shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem() shouldBe Stage.Success
        }

        coVerify(exactly = 0) {
            databaseMock.insert(getMockEntity(defaultTriplets))
        }
    }

    @Test
    fun apiCallThrowsException() = runBlocking {
        coEvery { apiMock.getArticleById(1) } throws IOException()

        useCase.execute(1).shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe IOException()
        }
    }

    @Test
    fun cacheThrowsException() = runBlocking {
        coEvery { apiMock.getArticleById(1) } returns mockSuccessResponse(getMockDto())
        coEvery { databaseMock.getRespectiveTriplets(1) } throws UnknownError()

        useCase.execute(1).shouldBeInOrder {
            awaitItem() shouldBe Stage.Initial
            awaitItem().exception shouldBe UnknownError()
        }
    }
}
