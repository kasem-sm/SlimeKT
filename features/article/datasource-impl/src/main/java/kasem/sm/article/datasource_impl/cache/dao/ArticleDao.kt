/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource_impl.cache.dao

import androidx.room.*
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<ArticleEntity>)

    @Transaction
    @Query("SELECT * FROM table_article WHERE id = :id")
    fun getArticleById(id: Int): Flow<ArticleEntity?>

    // Only 1 article will be active
    @Query("SELECT * FROM table_article WHERE is_active = 1")
    fun getAllActiveArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM table_article WHERE is_active = 1")
    fun getAllActiveArticlesNonFlow(): List<ArticleEntity>

    @Query("SELECT * FROM table_article WHERE is_shown = 1")
    suspend fun getAllArticlesShowInDailyRead(): List<ArticleEntity>

    @Query("SELECT * FROM table_article WHERE is_in_explore = 1")
    fun getArticlesInExplore(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM table_article WHERE is_in_explore = 1")
    suspend fun getArticlesInExploreNonFlow(): List<ArticleEntity>

    @Query("UPDATE table_article SET is_shown = :status WHERE id = :id")
    suspend fun updateDailyReadStatus(status: Boolean, id: Int)

    @Query("UPDATE table_article SET is_active = :active WHERE id = :id")
    suspend fun updateIsActiveInDailyReadStatus(active: Boolean, id: Int)

    @Query("UPDATE table_article SET is_in_explore = 0")
    suspend fun clearArticlesInExplore()

    @Query("DELETE From table_article")
    suspend fun removeAllArticles()

    @Query("SELECT * FROM table_article WHERE is_in_bookmark = 1")
    suspend fun getArticlesInBookmarkNonFlow(): List<ArticleEntity>

    @Query("SELECT * FROM table_article WHERE is_in_bookmark = 1")
    fun getArticlesInBookmark(): Flow<List<ArticleEntity>>

    @Query("UPDATE table_article SET is_in_bookmark = :status WHERE id = :id")
    suspend fun updateBookmarkStatus(status: Boolean, id: Int)

    @Query("UPDATE table_article SET is_in_bookmark = 0")
    suspend fun resetAllBookmarks()

    @Query(
        """
        SELECT * FROM table_article 
        WHERE title LIKE '%' || :query || '%'
        OR author LIKE '%' || :query || '%'
        OR topic LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
    """
    )
    fun getAllArticles(query: String): Flow<List<ArticleEntity>>

    @Query(
        """
        SELECT * FROM table_article 
        WHERE topic = :topic
        ORDER BY timestamp DESC
    """
    )
    fun getArticlesByTopic(topic: String): Flow<List<ArticleEntity>>

    @Query(
        """
        SELECT * FROM table_article 
        WHERE is_in_explore = 1 
        ORDER BY timestamp DESC
        LIMIT (:page * :pageSize)
    """
    )
    fun getArticlesForRecommend(page: Int, pageSize: Int): Flow<List<ArticleEntity>>
}
