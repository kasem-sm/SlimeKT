/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.model.Article
import kasem.sm.core.domain.ObserverInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveArticle @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val mapper: ArticleMapper
) : ObserverInteractor<Int, Article?>() {
    override suspend fun execute(params: Int): Flow<Article?> {
        return cache.getArticleById(params).map {
            mapper.map(it)
        }
    }
}
