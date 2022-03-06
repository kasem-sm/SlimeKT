/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kasem.sm.article.domain.interactors.GetArticle
import kasem.sm.article.domain.interactors.ObserveArticle
import kasem.sm.common_test_utils.ThreadExceptionTestRule
import kasem.sm.common_test_utils.shouldBe
import kasem.sm.core.domain.ObservableLoader
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.collect
import kasem.sm.ui_detail.utils.getMockDomain
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailVMTest {
    @get:Rule
    val uncaughtExceptionHandler = ThreadExceptionTestRule()

    private lateinit var viewModel: DetailVM

    private val getArticle: GetArticle = mockk(relaxed = true) {
    }

    private val observeArticle: ObserveArticle = mockk(relaxed = true) {
        coEvery {
            join(
                coroutineScope = TestScope(UnconfinedTestDispatcher()),
                onError = { },
                params = 1
            )
        } just runs

        coEvery { flow } returns flow {
            emit(getMockDomain())
        }
    }

    @Before
    fun setUp() {
        coEvery { getArticle.execute(1) } returns flow {
            emit(Stage.Exception())
        }

        coEvery {
            getArticle.execute(1).collect(
                loader = ObservableLoader(),
                onError = { },
            )
        } just runs

        viewModel = DetailVM(
            getArticle = getArticle,
            observeArticle = observeArticle,
            dispatchers = SlimeDispatchers.createTestDispatchers(UnconfinedTestDispatcher()),
            savedStateHandle = SavedStateHandle()
        )
    }

    @Test
    fun testStateChange() = runTest {
        viewModel.state.test {
            val data = awaitItem()
            data shouldBe DetailState(isLoading = false, article = getMockDomain())
        }
    }

//    @Test
//    fun testUiEvent() = runTest {
//        viewModel.uiEvent.test {
//            viewModel.refresh()
//            awaitItem() shouldBe UiEvent.Success
//            cancelAndConsumeRemainingEvents()
//        }
//    }
}
