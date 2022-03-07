/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import io.mockk.clearAllMocks
import kasem.sm.article.domain.interactors.data.FakeGetPagedArticles
import kasem.sm.article.domain.interactors.utils.getMockDomain
import kasem.sm.article.domain.model.Article
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.core.domain.PaginationStage
import kasem.sm.core.domain.SlimePaginationStatus
import kasem.sm.core.domain.SlimePaginationStatus.EndOfPaginationStatus
import kasem.sm.core.domain.SlimePaginationStatus.OnLoadingStatus
import kasem.sm.core.domain.SlimePaginationStatus.OnPageLoaded
import kasem.sm.core.domain.SlimePaginator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SlimePaginatorTest {

    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private val getPagedArticles = FakeGetPagedArticles()

    private var inTestList = mutableListOf<SlimePaginationStatus<List<Article>>>()

    private val slimePaginator = SlimePaginator(
        requestForNextPage = {
            getPagedArticles.mock.execute("", "", 0, 0)
        },
        currentStatus = {
            inTestList.add(it)
        },
        page = 0
    )

    @Before
    internal fun setUp() {
        clearAllMocks()
    }

    @After
    fun purgeList() {
        inTestList.clear()
    }

    @Test
    fun `when exception is Pagination Over, Paginator handles it separately`() = runTest {
        getPagedArticles.mockAndReturn(PaginationStage.PaginationOver)

        slimePaginator.execute()

        inTestList shouldBe listOf(
            OnLoadingStatus(true),
            EndOfPaginationStatus(true),
            OnLoadingStatus(false)
        )
    }

    @Test
    fun `when exception is thrown, Paginator handles it`() = runTest {
        getPagedArticles.mockAndReturn(PaginationStage.Exception(UnknownError()))

        slimePaginator.execute()

        val throwable = (inTestList[1] as SlimePaginationStatus.OnException).throwable

        inTestList shouldBe listOf(
            OnLoadingStatus(true),
            SlimePaginationStatus.OnException(throwable),
            OnLoadingStatus(false)
        )

        throwable shouldBe UnknownError()
    }

    @Test
    fun `when list is Empty, Pagination Over is triggered`() = runTest {
        getPagedArticles.mockAndReturn(PaginationStage.Success(listOf()))

        slimePaginator.execute()

        inTestList shouldBe listOf(
            OnLoadingStatus(true),
            EndOfPaginationStatus(true),
            OnLoadingStatus(false)
        )
    }

    @Test
    fun `when page is explicitly provided, end of pagination & loading status resets`() =
        runTest {
            getPagedArticles.mockAndReturn(
                PaginationStage.Success(listOf(getMockDomain()))
            )

            slimePaginator.execute(2)

            inTestList shouldBe listOf(
                EndOfPaginationStatus(false),
                OnLoadingStatus(true),
                OnPageLoaded(page = 3, data = listOf(getMockDomain())),
                OnLoadingStatus(false)
            )
        }

    @Test
    fun `when API exception is thrown`() = runTest {
        getPagedArticles.mockAndThrowErr()

        slimePaginator.execute()

        (inTestList[0] as SlimePaginationStatus.OnException).throwable shouldBe SecurityException()
    }
}
