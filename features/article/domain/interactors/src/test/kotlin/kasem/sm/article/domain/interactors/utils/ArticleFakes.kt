/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors.utils

import kasem.sm.article.datasource.cache.Quad
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.network.response.ArticleDto
import kasem.sm.article.datasource.network.response.ArticleResponse
import kasem.sm.article.datasource.network.response.SlimeResponse
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsBookmarked
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

    fun getMockEntity(quadData: Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked> = defaultQuadData): ArticleEntity {
        return getMockDto().toEntity(quadData)
    }

    suspend fun ArticleEntity.toDomain(quadData: Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked> = defaultQuadData): Article {
        return ArticleMapper().map(this).copy(
            isShownInDailyRead = quadData.first.dailyReadStatus,
            isActiveInDailyRead = quadData.second.isActiveInDailyRead,
            isInExplore = quadData.third.isInExplore,
            isInBookmark = quadData.fourth.isBookmarked
        )
    }

    suspend fun getMockDomain(): Article {
        return ArticleMapper().map(getMockEntity(defaultQuadData))
    }

    val defaultQuadData: Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked>
        get() =
            Quad(
                DailyReadStatus(dailyReadStatus = true),
                IsActiveInDailyRead(isActiveInDailyRead = true),
                IsInExplore(isInExplore = true),
                IsBookmarked(true)
            )

    val defaultQuadDataWithOneFalse: Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked>
        get() =
            Quad(
                DailyReadStatus(dailyReadStatus = false),
                IsActiveInDailyRead(isActiveInDailyRead = true),
                IsInExplore(isInExplore = true),
                IsBookmarked(true)
            )

    fun mockArticleResponse(): ArticleResponse {
        return ArticleResponse(
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
