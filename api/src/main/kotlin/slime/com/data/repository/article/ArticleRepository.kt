/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.article

import slime.com.data.models.Article

interface ArticleRepository {
    suspend fun createArticle(article: Article): Boolean
    suspend fun deleteArticle(articleId: Int): Boolean
    suspend fun getArticleById(articleId: Int): Article?
    suspend fun getAllArticles(
        topic: String = "",
        query: String = "",
    ): List<Article>
    suspend fun getRandomArticleFromSubscription(userId: String? = null): Article?
    suspend fun getRecommendedArticles(
        userId: String? = null,
    ): List<Article>
}
