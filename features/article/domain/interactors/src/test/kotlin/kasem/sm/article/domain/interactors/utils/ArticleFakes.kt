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
import kasem.sm.article.domain.interactors.ArticleMapper
import kasem.sm.article.domain.model.Article

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

fun getMockEntity(pair: Pair<DailyReadStatus, IsActiveInDailyRead> = defaultPair): ArticleEntity {
    return getMockDto().toEntity(pair)
}

suspend fun getMockDomain(): Article {
    return ArticleMapper().map(getMockEntity(defaultPair))
}

val defaultPair: Pair<DailyReadStatus, IsActiveInDailyRead>
    get() =
        Pair(DailyReadStatus(isShown = true), IsActiveInDailyRead(isActive = true))

val defaultPairWithOneFalse: Pair<DailyReadStatus, IsActiveInDailyRead>
    get() =
        Pair(DailyReadStatus(isShown = false), IsActiveInDailyRead(isActive = true))

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
