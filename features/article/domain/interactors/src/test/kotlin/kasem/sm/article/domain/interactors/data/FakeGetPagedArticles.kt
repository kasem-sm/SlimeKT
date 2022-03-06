/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors.data

import io.mockk.coEvery
import io.mockk.mockk
import kasem.sm.article.domain.interactors.GetPagedArticles
import kasem.sm.article.domain.model.Article
import kasem.sm.core.domain.PaginationStage
import kotlinx.coroutines.flow.flowOf

class FakeGetPagedArticles {
    val mock: GetPagedArticles = mockk()

    fun mockAndThrowErr(someException: Exception = SecurityException()) {
        coEvery {
            mock.execute("", "", 0, 0)
        } throws someException
    }

    fun mockAndReturn(
        paginationStage: PaginationStage<List<Article>>
    ) {
        coEvery {
            mock.execute("", "", 0, 0)
        } returns flowOf(paginationStage)
    }
}
