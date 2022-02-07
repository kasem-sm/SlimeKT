/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.datasource.network

import kasem.sm.feature_article.datasource.network.response.ArticleDto
import kasem.sm.feature_article.datasource.network.response.ArticleResponse
import kasem.sm.feature_article.datasource.network.response.SlimeResponse

interface ArticleApiService {

    suspend fun getAllArticles(
        page: Int,
        pageSize: Int,
        category: String = "",
        query: String = "",
    ): Result<SlimeResponse<ArticleResponse>?>

    suspend fun getArticleById(
        id: Int
    ): Result<SlimeResponse<ArticleDto>?>

    suspend fun getRandomArticleFromSubscription(): Result<SlimeResponse<ArticleDto>?>
}
