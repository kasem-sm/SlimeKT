/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors.data

import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.cache.Quad
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsBookmarked
import kasem.sm.article.datasource.utils.IsInExplore
import kasem.sm.article.domain.interactors.utils.ArticleFakes
import kasem.sm.article.domain.interactors.utils.ArticleFakes.defaultQuadData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeArticleDb : ArticleDatabaseService {

    val list = mutableListOf<ArticleEntity>()

    private var showError = false

    var exception: Exception = Exception()

    fun throwException(exception: Exception) {
        this.exception = exception
        showError = true
    }

    override suspend fun insert(article: ArticleEntity) {
        if (showError) {
            throw exception
        } else list.add(ArticleFakes.getMockEntity())
    }

    override fun getAllArticles(query: String): Flow<List<ArticleEntity>> {
        return if (showError) throw exception else flowOf(list)
    }

    override fun getArticlesByTopic(topic: String): Flow<List<ArticleEntity>> {
        return if (showError) throw exception else flowOf(
            list
                .filter {
                    it.topic == topic
                }
        )
    }

    override fun getArticleById(id: Int): Flow<ArticleEntity?> {
        return if (showError) throw exception else flowOf(
            list
                .find {
                    it.id == id
                }
        )
    }

    override fun getActiveArticleFlow(): Flow<ArticleEntity?> {
        return if (showError) throw exception else flowOf(
            list
                .find {
                    it.isActiveInDailyRead
                }
        )
    }

    override fun getInExploreArticles(): Flow<List<ArticleEntity>> {
        return if (showError) throw exception else flowOf(
            list
                .filter {
                    it.isInExplore
                }
        )
    }

    override suspend fun getActiveArticle(): ArticleEntity? {
        return if (showError) throw exception else list
            .find {
                it.isActiveInDailyRead
            }
    }

    override suspend fun updateDailyReadStatus(status: Boolean, id: Int) {
        if (showError) throw exception else {
            val index = list.indexOfFirst {
                it.id == id
            }

            val article = list
                .find {
                    it.id == id
                }?.copy(isShownInDailyRead = status)

            article?.let {
                if (index != -1) {
                    list.add(index, it)
                }
            }
        }
    }

    override suspend fun updateIsActiveInDailyReadStatus(active: Boolean, id: Int) {
        if (showError) throw exception else {
            val index = list.indexOfFirst {
                it.id == id
            }

            val article = list
                .find {
                    it.id == id
                }?.copy(isActiveInDailyRead = active)

            article?.let {
                if (index != -1) {
                    list.add(index, it)
                }
            }
        }
    }

    override suspend fun getArticleData(id: Int): Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked> {
        return if (showError) throw exception else {
            defaultQuadData
        }
    }

    override suspend fun removePreviousActiveArticle() {
        if (showError) throw exception else {
            list.filter { it.isActiveInDailyRead }.forEachIndexed { index, _ ->
                list.removeAt(index)
            }
        }
    }

    override suspend fun removeAllArticlesFromExplore() {
        if (showError) throw exception else {
            list.filter { it.isInExplore }.forEachIndexed { index, _ ->
                list.removeAt(index)
            }
        }
    }

    override suspend fun removeAllArticles() {
        if (showError) throw exception else {
            list.clear()
        }
    }

    override fun getBookmarkedArticles(): Flow<List<ArticleEntity>> {
        return if (showError) throw exception else flowOf(
            list
                .filter {
                    it.isInBookmark
                }
        )
    }

    override suspend fun updateBookmarkStatus(status: Boolean, id: Int) {
        if (showError) throw exception else {
            val index = list.indexOfFirst {
                it.id == id
            }

            val article = list
                .find {
                    it.id == id
                }?.copy(isInBookmark = status)

            article?.let {
                if (index != -1) {
                    list.add(index, it)
                }
            }
        }
    }

    override suspend fun resetAllBookmarks() {
        if (showError) throw exception else {
            list.filter { it.isInBookmark }.forEachIndexed { index, _ ->
                list.removeAt(index)
            }
        }
    }
}
