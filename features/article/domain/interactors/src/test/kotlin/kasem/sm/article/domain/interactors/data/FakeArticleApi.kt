/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors.data

import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.datasource.network.response.ArticleDto
import kasem.sm.article.datasource.network.response.ArticleResponse
import kasem.sm.article.datasource.network.response.SlimeResponse
import kasem.sm.article.domain.interactors.utils.ArticleFakes.getMockDto

class FakeArticleApi : ArticleApiService {

    private var showError = false

    private var exception: Exception = Exception()

    fun throwException(exception: Exception) {
        this.exception = exception
        showError = true
    }

    override suspend fun getAllArticles(
        topic: String,
        query: String
    ): Result<SlimeResponse<ArticleResponse>?> {
        return if (showError) Result.failure(exception) else {
            Result.success(
                SlimeResponse(
                    data = ArticleResponse(listOf(getMockDto())),
                    success = true
                )
            )
        }
    }

    override suspend fun getArticleById(id: Int): Result<SlimeResponse<ArticleDto>?> {
        return if (showError) Result.failure(exception) else {
            Result.success(
                SlimeResponse(
                    data = getMockDto(),
                    success = true
                )
            )
        }
    }

    override suspend fun getRandomArticleFromSubscription(): Result<SlimeResponse<ArticleDto>?> {
        return if (showError) Result.failure(exception) else {
            Result.success(
                SlimeResponse(
                    data = getMockDto(),
                    success = true
                )
            )
        }
    }

    override suspend fun getExploreArticles(): Result<SlimeResponse<List<ArticleDto>>> {
        return if (showError) Result.failure(exception) else {
            Result.success(
                SlimeResponse(
                    data = listOf(getMockDto()),
                    success = true
                )
            )
        }
    }
}
