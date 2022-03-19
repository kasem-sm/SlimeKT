/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.network.response

import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsInExplore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    @SerialName("info")
    val info: InfoDto? = null,
    @SerialName("articles")
    val articles: List<ArticleDto>? = null
)

@Serializable
data class InfoDto(
    @SerialName("articleSize")
    val size: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("prevPage")
    val prevPage: Int? = null,
    @SerialName("nextPage")
    val nextPage: Int? = null,
)

@Serializable
data class ArticleDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("featuredImage")
    val featuredImage: String,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("author")
    val author: String,
    @SerialName("topic")
    val topic: String
) {
    fun toEntity(
        pair: Triple<DailyReadStatus, IsActiveInDailyRead, IsInExplore>,
    ): ArticleEntity {
        return ArticleEntity(
            id = id,
            title = title,
            description = description,
            topic = topic,
            featuredImage = featuredImage,
            author = author,
            timestamp = timestamp,
            isShownInDailyRead = pair.first.isShown,
            isActiveInDailyRead = pair.second.isActive,
            isInExplore = pair.third.inExplore
        )
    }
}
