/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.cache

import java.io.Serializable
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsBookmarked
import kasem.sm.article.datasource.utils.IsInExplore
import kotlinx.coroutines.flow.Flow

interface ArticleDatabaseService {
    suspend fun insert(article: ArticleEntity)

    fun getAllArticles(query: String): Flow<List<ArticleEntity>>

    fun getArticlesByTopic(topic: String): Flow<List<ArticleEntity>>

    fun getArticleById(id: Int): Flow<ArticleEntity?>

    fun getActiveArticleFlow(): Flow<ArticleEntity?>

    fun getInExploreArticles(): Flow<List<ArticleEntity>>

    suspend fun getActiveArticle(): ArticleEntity?

    suspend fun updateDailyReadStatus(status: Boolean, id: Int)

    suspend fun updateIsActiveInDailyReadStatus(active: Boolean, id: Int)

    suspend fun getData(id: Int): Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked>

    suspend fun removePreviousActiveArticle()

    suspend fun removeAllArticlesFromExplore()

    suspend fun removeAllArticles()

    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    suspend fun updateBookmarkStatus(status: Boolean, id: Int)

    suspend fun resetAllBookmarks()
}

data class Quad<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Serializable {
    override fun toString(): String {
        return "Quad[$first, $second, $third, $fourth]"
    }
}
