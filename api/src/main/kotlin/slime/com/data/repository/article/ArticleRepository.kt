package slime.com.data.repository.article

import slime.com.data.models.Article

interface ArticleRepository {
    suspend fun createArticle(article: Article): Boolean
    suspend fun deleteArticle(articleId: Int): Boolean
    suspend fun getArticleById(articleId: Int): Article?
    suspend fun getAllArticles(
        topic: String = "",
        query: String,
        page: Int = 0,
        pageSize: Int = 10
    ): Pair<List<Article>, Int>
    suspend fun getRandomArticleFromSubscription(userId: String? = null): Article?
    suspend fun getRecommendedArticles(
        userId: String? = null,
        page: Int = 0,
        pageSize: Int = 10
    ): List<Article>
}
