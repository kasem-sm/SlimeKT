package slime.com.data.repository.article

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex
import slime.com.data.models.Article
import slime.com.data.models.Category
import slime.com.data.models.SubscribedCategory
import slime.com.service.SubscriptionService

class ArticleRepositoryImpl(
    db: CoroutineDatabase,
    private val subscriptionService: SubscriptionService
) : ArticleRepository {

    private val articleDb = db.getCollection<Article>()

    private val categoryDb = db.getCollection<Category>()

    private val subscribedCategories = db.getCollection<SubscribedCategory>()

    private suspend fun insertIfNotAlready(category: Category): Boolean {
        val ifFound = categoryDb.find().toList().find {
            it.name == category.name
        } != null
        return if (ifFound) {
            false
        } else {
            categoryDb.insertOne(category).wasAcknowledged()
        }
    }

    override suspend fun createArticle(article: Article): Boolean {
        insertIfNotAlready(Category(article.category))
        return articleDb.insertOne(article).wasAcknowledged()
    }

    override suspend fun deleteArticle(articleId: Int): Boolean {
        return articleDb.deleteOneById(articleId).wasAcknowledged()
    }

    override suspend fun getArticleById(articleId: Int): Article? {
        return articleDb.findOneById(articleId)
    }

    override suspend fun getAllArticles(
        category: String,
        query: String,
        page: Int,
        pageSize: Int
    ): Pair<List<Article>, Int> {
        val articles = when {
            category.isEmpty() && query.isNotEmpty() -> {
                articleDb.find().filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).skipAndMap(page, pageSize)
            }
            category.isNotEmpty() && query.isEmpty() -> {
                articleDb.find(
                    or(Article::category eq category)
                ).skipAndMap(page, pageSize)
            }
            category.isEmpty() -> {
                articleDb.find().skipAndMap(page, pageSize)
            }
            query.isEmpty() -> {
                articleDb.find(
                    or(Article::category eq category)
                ).skipAndMap(page, pageSize)
            }
            else -> {
                articleDb.find(
                    or(Article::category eq category)
                ).filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).skipAndMap(page, pageSize)
            }
        }

        val size = when {
            category.isEmpty() && query.isNotEmpty() -> {
                articleDb.find().filter(
                    or(
                        Article::title regex Regex("(?i).*$query.*"),
                        Article::author regex Regex("(?i).*$query.*"),
                    )
                ).toList().count()
            }
            category.isNotEmpty() && query.isEmpty() -> {
                articleDb.find(
                    or(Article::category eq category)
                ).toList().count()
            }
            category.isEmpty() -> {
                articleDb.find().toList().count()
            }
            query.isEmpty() -> {
                articleDb.find(
                    or(Article::category eq category)
                ).toList().count()
            }
            else -> {
                articleDb.find(
                    or(Article::category eq category)
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
            val userSubscribedCategory = subscribedCategories.find(SubscribedCategory::userId eq userId).toList().map {
                categoryDb.findOneById(it.categoryId) ?: return null
            }

            val randomCategory = userSubscribedCategory.random()
            articleDb.find(
                or(Article::category eq randomCategory.name)
            ).toList().randomOrNull()
        } else {
            articleDb.find().toList().randomOrNull()
        }
    }

    override suspend fun getRecommendedArticles(userId: String?, page: Int, pageSize: Int): List<Article> {
        return if (userId != null) {
            val categoriesInExplore = subscriptionService.getCategoriesNotSubscribed(userId).random()
            articleDb.find(Article::category eq categoriesInExplore.name).skipAndMap(0, 4)
        } else {
            val randomCategory = categoryDb.find().toList().random()
            articleDb.find(Article::category eq randomCategory.name).skipAndMap(0, 4)
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
                category = it.category
            )
        }
    }
}
