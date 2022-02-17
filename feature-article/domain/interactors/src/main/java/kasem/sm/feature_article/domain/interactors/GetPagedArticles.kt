/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.PaginationOver
import kasem.sm.core.domain.PaginationStage
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.pagingStage
import kasem.sm.feature_article.datasource.cache.ArticleDatabaseService
import kasem.sm.feature_article.datasource.network.ArticleApiService
import kasem.sm.feature_article.datasource.network.response.ArticleDto
import kasem.sm.feature_article.domain.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GetPagedArticles @Inject constructor(
    private val api: ArticleApiService,
    private val cache: ArticleDatabaseService,
    private val applicationScope: CoroutineScope,
    private val slimeDispatchers: SlimeDispatchers,
    private val mapper: ArticleMapper
) {
    fun execute(
        category: String,
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<PaginationStage<List<Article>>> {
        return slimeDispatchers.default.pagingStage {
            // Query API and cache Data
            queryAndCacheData(category, query, page, pageSize)

            /**
             * Pagination on our server starts from 0 and when requesting data
             * from cache starting from page 0, the last page doesn't loads
             * for some unknown reason. Until I figure it out, I will be
             * paginating data from cache starting
             * from page 1.
             */
            getFromCache(category, page + 2, pageSize, query)
        }
    }

    private suspend fun getFromCache(
        category: String,
        page: Int,
        pageSize: Int,
        query: String
    ): List<Article> = cache.getPagedArticles(
        category = category,
        page = page,
        pageSize = pageSize,
        query = query
    ).map { e ->
        mapper.map(e)
    }

    private suspend fun queryAndCacheData(
        category: String,
        query: String,
        page: Int,
        pageSize: Int,
    ) {
        val apiResponse = api.getAllArticles(page, pageSize, category, query).getOrThrow()

        apiResponse?.data?.articles?.cacheData()

        if (page > apiResponse?.data?.info?.totalPages!!) {
            throw PaginationOver()
        }
    }

    private suspend fun List<ArticleDto>.cacheData() {
        applicationScope.launch {
            map {
                val pair = cache.getRespectivePair(it.id)
                cache.insert(it.toEntity(pair))
            }
        }.join()
    }
}
