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
        page: Int,
        pageSize: Int
    ): Pair<List<Article>, Int> {
        val articles = when {
            topic.isEmpty() && query.isNotEmpty() -> {
                articleDb.find().filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).skipAndMap(page, pageSize)
            }
            topic.isNotEmpty() && query.isEmpty() -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).skipAndMap(page, pageSize)
            }
            topic.isEmpty() -> {
                articleDb.find().skipAndMap(page, pageSize)
            }
            query.isEmpty() -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).skipAndMap(page, pageSize)
            }
            else -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).skipAndMap(page, pageSize)
            }
        }

        val size = when {
            topic.isEmpty() && query.isNotEmpty() -> {
                articleDb.find().filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).toList().count()
            }
            topic.isNotEmpty() && query.isEmpty() -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).toList().count()
            }
            topic.isEmpty() -> {
                articleDb.find().toList().count()
            }
            query.isEmpty() -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).toList().count()
            }
            else -> {
                articleDb.find(
                    or(Article::topic eq topic)
                ).filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).toList().count()
            }
        }

        return Pair(articles, size)
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

    override suspend fun getRecommendedArticles(userId: String?, page: Int, pageSize: Int): List<Article> {
        return if (userId != null) {
            val topicsInExploreOrRandom =
                subscriptionService.getTopicsNotSubscribed(userId).randomOrNull() ?: topicDb.find().toList().random()
            articleDb.find(Article::topic eq topicsInExploreOrRandom.name).skipAndMap(0, 4)
        } else {
            val randomTopic = topicDb.find().toList().random()
            articleDb.find(Article::topic eq randomTopic.name).skipAndMap(0, 4)
        }
    }

    private suspend fun CoroutineFindPublisher<Article>.skipAndMap(page: Int, pageSize: Int): List<Article> {
        return this.skip(page * pageSize).limit(pageSize).descendingSort(Article::timestamp).toList().map {
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
