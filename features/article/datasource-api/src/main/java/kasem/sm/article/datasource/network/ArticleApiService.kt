/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.network

import kasem.sm.article.datasource.network.response.ArticleDto
import kasem.sm.article.datasource.network.response.ArticleResponse
import kasem.sm.article.datasource.network.response.SlimeResponse

interface ArticleApiService {

    suspend fun getAllArticles(
        topic: String = "",
        query: String = "",
    ): Result<SlimeResponse<ArticleResponse>?>

    suspend fun getArticleById(
        id: Int
    ): Result<SlimeResponse<ArticleDto>?>

    suspend fun getRandomArticleFromSubscription(): Result<SlimeResponse<ArticleDto>?>

    suspend fun getExploreArticles(): Result<SlimeResponse<List<ArticleDto>>>
}
