/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.observers

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.interactors.ArticleMapper
import kasem.sm.article.domain.model.Article
import kasem.sm.core.domain.ObserverInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveArticles @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val mapper: ArticleMapper
) : ObserverInteractor<String, List<Article>>() {
    override suspend fun execute(params: String): Flow<List<Article>> {
        val pagedArticles = cache.getAllArticles(
            query = params
        ).map {
            mapper.map(it)
        }
        return pagedArticles
    }
}

class ObserveArticlesByTopic @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val mapper: ArticleMapper
) : ObserverInteractor<String, List<Article>>() {
    override suspend fun execute(params: String): Flow<List<Article>> {
        val pagedArticles = cache.getArticlesByTopic(
            topic = params
        ).map {
            mapper.map(it)
        }
        return pagedArticles
    }
}
