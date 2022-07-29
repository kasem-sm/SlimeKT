/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.datasource.utils.IsInExplore
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.core.utils.getOrDefault
import kotlinx.coroutines.flow.Flow

class GetInExploreArticles @Inject constructor(
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(): Flow<Stage> {
        return dispatchers.default.start {
            val articles = api.getExploreArticles()
                .getOrThrow().data.getOrDefault()

            cache.removeAllArticlesFromExplore()

            articles.map {
                val updatedData = cache.getArticleData(it.id)
                    .copy(third = IsInExplore(true))
                cache.insert(it.toEntity(updatedData))
            }
        }
    }
}
