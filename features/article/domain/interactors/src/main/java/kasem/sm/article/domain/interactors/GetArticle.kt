/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticle @Inject constructor(
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(articleId: Int): Flow<Stage> {
        return dispatchers.default.start {
            val articleFromApi = api.getArticleById(articleId)
                .getOrThrow()

            articleFromApi?.data?.let {
                val data = cache.getArticleData(it.id)
                cache.insert(it.toEntity(data))
            }
        }
    }
}
