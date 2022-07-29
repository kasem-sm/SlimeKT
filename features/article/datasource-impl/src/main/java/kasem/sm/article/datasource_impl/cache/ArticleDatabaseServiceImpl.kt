/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource_impl.cache

import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.cache.Quad
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsBookmarked
import kasem.sm.article.datasource.utils.IsInExplore
import kasem.sm.article.datasource_impl.cache.dao.ArticleDao
import kasem.sm.core.utils.slimeSuspendTry
import kasem.sm.core.utils.slimeTry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ArticleDatabaseServiceImpl @Inject constructor(
    private val dao: ArticleDao
) : ArticleDatabaseService {

    override suspend fun insert(article: ArticleEntity) {
        slimeSuspendTry {
            dao.insert(article)
        }
    }

    override fun getArticleById(id: Int): Flow<ArticleEntity?> {
        return slimeTry {
            dao.getArticleById(id)
        }
    }

    override fun getActiveArticleFlow(): Flow<ArticleEntity?> {
        return slimeTry {
            dao.getAllActiveArticles().map {
                it.firstOrNull()
            }
        }
    }

    override fun getInExploreArticles(): Flow<List<ArticleEntity>> {
        return slimeTry {
            dao.getArticlesForRecommend(1, 4)
        }
    }

    override suspend fun getActiveArticle(): ArticleEntity? {
        return slimeSuspendTry {
            dao.getAllActiveArticlesNonFlow().firstOrNull()
        }
    }

    override fun getAllArticles(
        query: String,
    ): Flow<List<ArticleEntity>> {
        return slimeTry {
            dao.getAllArticles(query)
        }
    }

    override fun getArticlesByTopic(topic: String): Flow<List<ArticleEntity>> {
        return slimeTry {
            dao.getArticlesByTopic(topic)
        }
    }

    override suspend fun updateDailyReadStatus(status: Boolean, id: Int) {
        slimeSuspendTry {
            dao.updateDailyReadStatus(status, id)
        }
    }

    override suspend fun updateIsActiveInDailyReadStatus(active: Boolean, id: Int) {
        slimeSuspendTry {
            dao.updateIsActiveInDailyReadStatus(active, id)
        }
    }

    override suspend fun getArticleData(id: Int): Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked> {
        return slimeSuspendTry {
            Quad(
                DailyReadStatus(isShown(id)),
                IsActiveInDailyRead(isActive(id)),
                IsInExplore((inExplore(id))),
                IsBookmarked(isBookmarked(id))
            )
        }
    }

    override suspend fun removePreviousActiveArticle() {
        slimeSuspendTry {
            updateIsActiveInDailyReadStatus(false, getActiveArticle()?.id ?: return@slimeSuspendTry)
        }
    }

    override suspend fun removeAllArticlesFromExplore() {
        slimeSuspendTry {
            dao.clearArticlesInExplore()
        }
    }

    override suspend fun removeAllArticles() {
        slimeSuspendTry {
            dao.removeAllArticles()
        }
    }

    override fun getBookmarkedArticles(): Flow<List<ArticleEntity>> {
        return slimeTry {
            dao.getArticlesInBookmark()
        }
    }

    override suspend fun updateBookmarkStatus(status: Boolean, id: Int) {
        slimeSuspendTry {
            dao.updateBookmarkStatus(status, id)
        }
    }

    override suspend fun resetAllBookmarks() {
        slimeSuspendTry {
            dao.resetAllBookmarks()
        }
    }

    private suspend fun isActive(id: Int): Boolean {
        return slimeSuspendTry {
            dao.getAllActiveArticlesNonFlow().any {
                id == it.id
            }
        }
    }

    private suspend fun isShown(id: Int): Boolean {
        return slimeSuspendTry {
            dao.getAllArticlesShowInDailyRead().any {
                id == it.id
            }
        }
    }

    private suspend fun inExplore(id: Int): Boolean {
        return slimeSuspendTry {
            dao.getArticlesInExploreNonFlow().any {
                id == it.id
            }
        }
    }

    private suspend fun isBookmarked(id: Int): Boolean {
        return slimeSuspendTry {
            dao.getArticlesInBookmarkNonFlow().any {
                id == it.id
            }
        }
    }
}
