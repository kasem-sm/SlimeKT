/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.cache

import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsInExplore
import kotlinx.coroutines.flow.Flow

interface ArticleDatabaseService {
    suspend fun insert(article: ArticleEntity)

    fun getArticleById(id: Int): Flow<ArticleEntity?>

    fun getActiveArticleFlow(): Flow<ArticleEntity?>

    fun getInExploreArticles(): Flow<List<ArticleEntity>>

    suspend fun getActiveArticle(): ArticleEntity?

    suspend fun getPagedArticles(
        page: Int,
        pageSize: Int,
        topic: String = "",
        query: String = "",
    ): List<ArticleEntity>

    suspend fun getArticlesTillPage(
        page: Int,
        pageSize: Int,
        topic: String = "",
        query: String = "",
    ): List<ArticleEntity>

    suspend fun updateDailyReadStatus(status: Boolean, id: Int)

    suspend fun updateIsActiveInDailyReadStatus(active: Boolean, id: Int)

    suspend fun getRespectiveTriplets(id: Int): Triple<DailyReadStatus, IsActiveInDailyRead, IsInExplore>

    suspend fun removePreviousActiveArticle()

    suspend fun removeAllArticlesFromExplore()
}
