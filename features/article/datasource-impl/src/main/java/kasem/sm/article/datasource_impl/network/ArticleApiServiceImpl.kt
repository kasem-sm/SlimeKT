/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource_impl.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.datasource.network.response.ArticleDto
import kasem.sm.article.datasource.network.response.ArticleResponse
import kasem.sm.article.datasource.network.response.SlimeResponse
import kasem.sm.auth_api.AuthManager
import kasem.sm.auth_api.ID
import kasem.sm.core.utils.userIdParam
import kasem.sm.core.utils.withResult
import javax.inject.Inject

internal class ArticleApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val authManager: AuthManager
) : ArticleApiService {
    override suspend fun getAllArticles(
        topic: String,
        query: String,
    ): Result<SlimeResponse<ArticleResponse>?> {
        return withResult {
            client.get(GET_ALL_ARTICLE_ROUTE) {
                parameter("topic", topic)
                parameter("query", query)
            }.body()
        }
    }

    override suspend fun getArticleById(id: Int): Result<SlimeResponse<ArticleDto>?> {
        return withResult {
            client.get(GET_ARTICLE_BY_ID_ROUTE) {
                parameter("id", id)
            }.body()
        }
    }

    override suspend fun getRandomArticleFromSubscription(): Result<SlimeResponse<ArticleDto>?> {
        return withResult {
            client.get(GET_RANDOM_ARTICLE_ROUTE) {
                userIdParam(id = authManager.getUserData(ID))
            }.body()
        }
    }

    override suspend fun getExploreArticles(): Result<SlimeResponse<List<ArticleDto>>> {
        return withResult {
            client.get(GET_EXPLORE_ARTICLES_ROUTE) {
                userIdParam(id = authManager.getUserData(ID))
            }.body()
        }
    }

    companion object EndPoints {
        const val GET_ALL_ARTICLE_ROUTE = "/api/article/all"
        const val GET_ARTICLE_BY_ID_ROUTE = "/api/article/get"
        const val GET_RANDOM_ARTICLE_ROUTE = "/api/article/get/random"
        const val GET_EXPLORE_ARTICLES_ROUTE = "/api/article/explore"
    }
}
