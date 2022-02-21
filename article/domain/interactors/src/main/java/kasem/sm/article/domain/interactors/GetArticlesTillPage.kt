/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.domain.model.Article
import kasem.sm.core.domain.PaginationStage
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.pagingStage
import kotlinx.coroutines.flow.Flow

class GetArticlesTillPage @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val slimeDispatchers: SlimeDispatchers,
    private val mapper: ArticleMapper
) {
    fun execute(params: Param): Flow<PaginationStage<List<Article>>> {
        return slimeDispatchers.default.pagingStage {
            cache.getArticlesTillPage(
                query = params.query,
                category = params.category,
                page = params.page,
                pageSize = params.pageSize
            ).map {
                mapper.map(it)
            }
        }
    }
}
