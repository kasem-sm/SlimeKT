/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.article

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex
import slime.com.data.models.Article
import slime.com.data.models.SubscribedTopic
import slime.com.data.models.Topic
import slime.com.service.SubscriptionService

class ArticleRepositoryImpl(
    db: CoroutineDatabase,
    private val subscriptionService: SubscriptionService
) : ArticleRepository {

    private val articleDb = db.getCollection<Article>()

    private val topicDb = db.getCollection<Topic>()

    private val subscribedTopics = db.getCollection<SubscribedTopic>()

    private suspend fun insertIfNotAlready(topic: Topic): Boolean {
        val ifFound = topicDb.find().toList().find {
            it.name == topic.name
        } != null
        return if (ifFound) {
            false
        } else {
            topicDb.insertOne(topic).wasAcknowledged()
        }
    }

    override suspend fun createArticle(article: Article): Boolean {
        insertIfNotAlready(Topic(article.topic))
        return articleDb.insertOne(article).wasAcknowledged()
    }

    override suspend fun deleteArticle(articleId: Int): Boolean {
        return articleDb.deleteOneById(articleId).wasAcknowledged()
    }

    override suspend fun getArticleById(articleId: Int): Article? {
        return articleDb.findOneById(articleId)
    }

    override suspend fun getAllArticles(
        topic: String,
        query: String,
    ): List<Article> {
        val articles = when {
            topic.isEmpty() && query.isNotEmpty() -> {
                articleDb.find().filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).skipAndMap()
            }
            topic.isNotEmpty() && query.isEmpty() -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).skipAndMap()
            }
            topic.isEmpty() -> {
                articleDb.find().skipAndMap()
            }
            query.isEmpty() -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).skipAndMap()
            }
            else -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).skipAndMap()
            }
        }
        return articles
    }

    override suspend fun getRandomArticleFromSubscription(userId: String?): Article? {
        return if (userId != null) {
            val userSubscribedTopic = subscribedTopics.find(SubscribedTopic::userId eq userId).toList().map {
                topicDb.findOneById(it.topicId) ?: return null
            }

            val randomTopic = userSubscribedTopic.random()
            articleDb.find(
                or(Article::topic eq randomTopic.name)
            ).toList().randomOrNull()
        } else {
            articleDb.find().toList().randomOrNull()
        }
    }

    override suspend fun getRecommendedArticles(userId: String?): List<Article> {
        return try {
            if (userId != null) {
                val topicsInExplore = subscriptionService.getTopicsNotSubscribed(userId).randomOrNull() ?: return emptyList()
                articleDb.find(Article::topic eq topicsInExplore.name).toList().shuffled().take(4)
            } else {
                val randomTopic = topicDb.find().toList().randomOrNull() ?: return emptyList()
                articleDb.find(Article::topic eq randomTopic.name).toList().shuffled().take(4)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun CoroutineFindPublisher<Article>.skipAndMap(): List<Article> {
        return descendingSort(Article::timestamp).toList().map {
            Article(
                id = it.id,
                title = it.title,
                description = it.description,
                featuredImage = it.featuredImage,
                author = it.author,
                timestamp = it.timestamp,
                topic = it.topic
            )
        }
    }
}
