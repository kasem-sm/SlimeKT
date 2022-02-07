package slime.com.data.repository.article

import slime.com.data.models.Article

interface ArticleRepository {

    suspend fun createArticle(article: Article): Boolean

    suspend fun deleteArticle(articleId: String): Boolean

    suspend fun getArticleById(articleId: String): Article?

    suspend fun getAllArticles(
        category: String = "",
        query: String,
        page: Int = 0,
        pageSize: Int = 10
    ): Pair<List<Article>, Int>

    suspend fun getArticlesFromSubscription(
        userId: String,
        page: Int = 0,
        pageSize: Int = 10
    ): List<Article>

    suspend fun getRandomArticleFromSubscription(userId: String? = null): Article?
}
