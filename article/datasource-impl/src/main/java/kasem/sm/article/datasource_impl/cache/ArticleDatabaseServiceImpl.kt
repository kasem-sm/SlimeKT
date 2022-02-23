/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource_impl.cache

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.cache.dao.ArticleDao
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.core.utils.slimeSuspendTry
import kasem.sm.core.utils.slimeTry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override suspend fun getActiveArticle(): ArticleEntity? {
        return slimeSuspendTry {
            dao.getAllActiveArticlesNonFlow().firstOrNull()
        }
    }

    override suspend fun getPagedArticles(
        page: Int,
        pageSize: Int,
        topic: String,
        query: String,
    ): List<ArticleEntity> {
        return slimeSuspendTry {
            when {
                topic.isEmpty() && query.isNotEmpty() -> {
                    dao.getQueriedPagedArticle(
                        query,
                        page,
                        pageSize
                    )
                }
                topic.isNotEmpty() && query.isEmpty() -> {
                    dao.getTopicPagedArticles(
                        topic,
                        page,
                        pageSize
                    )
                }
                topic.isEmpty() -> {
                    dao.getPagedArticles(page, pageSize)
                }
                query.isEmpty() -> {
                    dao.getTopicPagedArticles(topic, page, pageSize)
                }
                else -> {
                    dao.getQueriedPagedArticles(topic, query, page, pageSize)
                }
            }
        }
    }

    // I also don't know what I have done lol but it works
    override suspend fun getArticlesTillPage(
        page: Int,
        pageSize: Int,
        topic: String,
        query: String,
    ): List<ArticleEntity> {
        return slimeSuspendTry {
            when {
                topic.isEmpty() && query.isNotEmpty() -> {
                    dao.getQueriedArticlesTillPage(
                        query,
                        page,
                        pageSize
                    )
                }
                topic.isNotEmpty() && query.isEmpty() -> {
                    dao.getTopicArticlesTillPage(
                        topic,
                        page,
                        pageSize
                    )
                }
                topic.isEmpty() -> {
                    dao.getArticlesTillPage(page, pageSize)
                }
                query.isEmpty() -> {
                    dao.getTopicArticlesTillPage(topic, page, pageSize)
                }
                else -> {
                    dao.getQueriedArticlesTillPage(topic, query, page, pageSize)
                }
            }
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

    override suspend fun getRespectivePair(id: Int): Pair<DailyReadStatus, IsActiveInDailyRead> {
        return slimeSuspendTry {
            Pair(
                DailyReadStatus(isShown(id)),
                IsActiveInDailyRead(isActive(id))
            )
        }
    }

    override suspend fun removePreviousActiveArticle() {
        updateIsActiveInDailyReadStatus(false, getActiveArticle()?.id ?: return)
    }

    private suspend fun isActive(id: Int): Boolean {
        return slimeSuspendTry {
            dao.getAllActiveArticlesNonFlow().any {
                id == it?.id
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
}
