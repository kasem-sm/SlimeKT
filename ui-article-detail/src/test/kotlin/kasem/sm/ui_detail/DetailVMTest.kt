/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kasem.sm.article.domain.interactors.GetArticle
import kasem.sm.article.domain.interactors.ObserveArticle
import kasem.sm.article.domain.model.Article
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.ui_core.showMessage
import kasem.sm.ui_detail.utils.ArticleFakes.getMockDomain
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DetailVMTest {
    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private lateinit var viewModel: DetailVM

    private val getArticle: GetArticle = mockk(relaxed = true)

    private var observeArticle: ObserveArticle = mockk(relaxUnitFun = true)

    private fun initViewModel(article: Article? = null) {
        coEvery { observeArticle.flow } returns flow {
            article?.let { emit(it) }
        }

        viewModel = DetailVM(
            getArticle = getArticle,
            observeArticle = observeArticle,
            dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher()),
            savedStateHandle = SavedStateHandle()
        )
    }

    @Test
    fun testStateEmits_ProperData() = runTest {
        initViewModel(getMockDomain())

        viewModel.state.test {
            val state = awaitItem()
            state shouldBe DetailState(
                isLoading = true,
                article = getMockDomain()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testArticleIsNull_When_CacheThrowsException() = runTest {
        initViewModel()

        coEvery { observeArticle.flow } throws IOException()

        viewModel.state.test {
            val state = awaitItem()
            state shouldBe DetailState(
                isLoading = true,
                article = null
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testUiEventEmit_ProperError() = runTest {
        initViewModel()

        coEvery { getArticle.execute(any()) } returns flow {
            emit(Stage.Exception(IOException()))
        }

        viewModel.uiEvent.test {
            viewModel.refresh()
            val event = awaitItem()
            event shouldBe showMessage("Something went wrong!")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
