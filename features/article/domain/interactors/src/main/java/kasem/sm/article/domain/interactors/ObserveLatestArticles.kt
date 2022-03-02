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
import kotlinx.coroutines.flow.flow

class ObserveLatestArticles @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val mapper: ArticleMapper
) : ObserverInteractor<Unit, List<Article>>() {
    override suspend fun execute(params: Unit): Flow<List<Article>> {
        return flow {
            val pagedArticles = cache.getPagedArticles(0, 4).map {
                mapper.map(it)
            }
            emit(pagedArticles)
        }
    }
}
