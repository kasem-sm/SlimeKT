/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors.utils

import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.network.response.ArticleDto
import kasem.sm.article.datasource.network.response.ArticleResponse
import kasem.sm.article.datasource.network.response.InfoDto
import kasem.sm.article.datasource.network.response.SlimeResponse
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsInExplore
import kasem.sm.article.domain.interactors.ArticleMapper
import kasem.sm.article.domain.model.Article

internal object ArticleFakes {

    fun getMockDto(): ArticleDto {
        return ArticleDto(
            id = 1,
            title = "Title",
            description = "Description",
            featuredImage = "featuredImage",
            timestamp = 1L,
            author = "author",
            topic = "Topic",
        )
    }

    fun getMockEntity(triple: Triple<DailyReadStatus, IsActiveInDailyRead, IsInExplore> = defaultTriplets): ArticleEntity {
        return getMockDto().toEntity(triple)
    }

    suspend fun ArticleEntity.toDomain(triple: Triple<DailyReadStatus, IsActiveInDailyRead, IsInExplore> = defaultTriplets): Article {
        return ArticleMapper().map(this).copy(
            isShownInDailyRead = triple.first.isShown,
            isActiveInDailyRead = triple.second.isActive,
            isInExplore = triple.third.inExplore
        )
    }

    suspend fun getMockDomain(): Article {
        return ArticleMapper().map(getMockEntity(defaultTriplets))
    }

    val defaultTriplets: Triple<DailyReadStatus, IsActiveInDailyRead, IsInExplore>
        get() =
            Triple(
                DailyReadStatus(isShown = true),
                IsActiveInDailyRead(isActive = true),
                IsInExplore(inExplore = true)
            )

    val defaultTripletsWithOneFalse: Triple<DailyReadStatus, IsActiveInDailyRead, IsInExplore>
        get() =
            Triple(
                DailyReadStatus(isShown = false),
                IsActiveInDailyRead(isActive = true),
                IsInExplore(inExplore = true)
            )

    fun mockArticleResponse(
        size: Int = 0,
        totalPage: Int = 0,
        prevPage: Int = 0,
        nextPage: Int = 0
    ): ArticleResponse {
        return ArticleResponse(
            info = InfoDto(size, totalPage, prevPage, nextPage),
            articles = listOf(getMockDto())
        )
    }

    fun <T> mockSuccessResponse(data: T?): Result<SlimeResponse<T>> {
        return Result.success(SlimeResponse("Success", data, true))
    }

    fun <T> mockSuccessResponseWithNullData(): Result<SlimeResponse<T>> {
        return Result.success(SlimeResponse("success", null, true))
    }
}
