/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.datasource.network.response

import kasem.sm.feature_article.datasource.cache.entity.ArticleEntity
import kasem.sm.feature_article.datasource.utils.DailyReadStatus
import kasem.sm.feature_article.datasource.utils.IsActiveInDailyRead
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
    @SerialName("category")
    val category: String
) {
    fun toEntity(pair: Pair<DailyReadStatus, IsActiveInDailyRead>): ArticleEntity {
        return ArticleEntity(
            id = id,
            title = title,
            description = description,
            featuredImage = featuredImage,
            author = author,
            timestamp = timestamp,
            isShownInDailyRead = pair.first.isShown,
            isActiveInDailyRead = pair.second.isActive,
            category = category
        )
    }
}
