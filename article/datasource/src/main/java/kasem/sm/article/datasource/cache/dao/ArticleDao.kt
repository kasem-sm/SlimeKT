/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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
    @Query("SELECT * FROM table_article ORDER BY timestamp DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Transaction
    @Query("SELECT * FROM table_article WHERE id = :id")
    fun getArticleById(id: Int): Flow<ArticleEntity?>

    // Only 1 article will be active
    @Query("SELECT * FROM table_article WHERE is_active = 1")
    fun getAllActiveArticles(): Flow<List<ArticleEntity>>

    // Only 1 article will be active
    @Query("SELECT * FROM table_article WHERE is_active = 1")
    fun getAllActiveArticlesNonFlow(): List<ArticleEntity?>

    @Query("SELECT * FROM table_article WHERE is_shown = 1")
    suspend fun getAllArticlesShowInDailyRead(): List<ArticleEntity>

    @Query("UPDATE table_article SET is_shown = :status WHERE id = :id")
    suspend fun updateDailyReadStatus(status: Boolean, id: Int)

    @Query("UPDATE table_article SET is_active = :active WHERE id = :id")
    suspend fun updateIsActiveInDailyReadStatus(active: Boolean, id: Int)

    /**
     * ------------
     * STARTS PAGED ARTICLE
     * ------------
     */

    @Query(
        """
        SELECT * FROM table_article 
        WHERE topic = :topic
        ORDER BY timestamp DESC
        LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """
    )
    // 1
    suspend fun getTopicPagedArticles(
        topic: String,
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    @Query(
        """
        SELECT * FROM table_article 
        ORDER BY timestamp DESC
        LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """
    )
    // 2
    suspend fun getPagedArticles(
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    @Query(
        """
        SELECT * FROM table_article 
        WHERE topic = :topic
        AND title LIKE '%' || :query || '%'
        OR author LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
        LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """
    )
    // 3
    suspend fun getQueriedPagedArticles(
        topic: String,
        query: String,
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    @Query(
        """
        SELECT * FROM table_article 
        WHERE title LIKE '%' || :query || '%'
        OR author LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
        LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """
    )
    // 4
    suspend fun getQueriedPagedArticle(
        query: String,
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    /**
     * ------------
     * ENDS PAGED ARTICLE
     * ------------
     */

    /**
     * -------------------------------------------------------------
     */

    /**
     * ------------
     * STARTS TILL PAGE ARTICLE
     * ------------
     */

    @Query(
        """
        SELECT * FROM table_article 
        WHERE topic = :topic
        ORDER BY timestamp DESC
        LIMIT (:page * :pageSize)
    """
    )
    // 1
    suspend fun getTopicArticlesTillPage(
        topic: String,
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    @Query(
        """
        SELECT * FROM table_article 
        WHERE topic = :topic
        AND title LIKE '%' || :query || '%'
        OR author LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
        LIMIT (:page * :pageSize)
    """
    )
    // 2
    suspend fun getQueriedArticlesTillPage(
        topic: String,
        query: String,
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    @Query(
        """
        SELECT * FROM table_article 
        WHERE title LIKE '%' || :query || '%'
        OR author LIKE '%' || :query || '%'
        ORDER BY timestamp DESC
        LIMIT (:page * :pageSize)
    """
    )
    // 3
    suspend fun getQueriedArticlesTillPage(
        query: String,
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    @Query(
        """
        SELECT * FROM table_article
        ORDER BY timestamp DESC
        LIMIT (:page * :pageSize)
    """
    )
    // 4
    suspend fun getArticlesTillPage(
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity>

    /**
     * ------------
     * END TILL PAGE ARTICLE
     * ------------
     */
}
