/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.core.utils.getOrDefault
import kotlinx.coroutines.flow.Flow

class GetLatestArticles @Inject constructor(
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(): Flow<Stage> {
        return dispatchers.default.start {
            val articles = api.getAllArticles(0, 10)
                .getOrThrow()?.data?.articles.getOrDefault()

            articles.map {
                val triple = cache.getRespectiveTriplets(it.id)
                cache.insert(it.toEntity(triple))
            }
        }
    }
}
