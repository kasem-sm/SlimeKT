/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.service

import slime.com.data.models.Article
import slime.com.data.repository.article.ArticleRepository
import slime.com.data.repository.recommended_topic.RecommendedTopicRepository
import slime.com.data.request.CreateArticleRequest
import slime.com.utils.ServiceResult

class ArticleService(
    private val articleRepository: ArticleRepository,
    private val recommendedTopicRepository: RecommendedTopicRepository
) {

    suspend fun getArticlesInExplore(userId: String?): List<Article> {
        return if (userId == null) {
            articleRepository.getRecommendedArticles()
        } else {
            val recommendedTopic = recommendedTopicRepository.getRecommendedTopic(userId)?.topicName
                ?: return articleRepository.getRecommendedArticles()
            return articleRepository.getAllArticles(recommendedTopic)
        }
    }

    suspend fun getRandomArticleFromUsersSubscription(userId: String? = null): Article? {
        return articleRepository.getRandomArticleFromSubscription(userId)
    }

    suspend fun getAllArticles(
        topic: String = "",
        query: String = ""
    ): List<Article> = articleRepository.getAllArticles(topic, query)

    suspend fun getArticleById(articleId: Int) = articleRepository.getArticleById(articleId)

    suspend fun deleteArticleById(articleId: Int) = articleRepository.deleteArticle(articleId)

    suspend fun validateAndCreateArticle(article: CreateArticleRequest): ServiceResult {
        article.apply {
            return when {
                (title.isBlank() || description.isBlank()) -> ServiceResult.Error("Required fields cannot be blank")
                (title.trim().length < 6) -> ServiceResult.Error("Article's title length should be of 6 characters or more")
                (description.trim().length < 10) -> ServiceResult.Error("Article's description should be of 10 characters or more")
                else -> createArticle(article)
            }
        }
    }

    private suspend fun createArticle(article: CreateArticleRequest): ServiceResult {
        return when (articleRepository.createArticle(article.toArticle())) {
            true -> ServiceResult.Success("Article created successfully")
            false -> ServiceResult.Error("Failed to create an article, please try again later")
        }
    }
}
