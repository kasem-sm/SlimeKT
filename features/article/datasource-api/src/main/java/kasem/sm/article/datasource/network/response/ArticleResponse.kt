/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.network.response

import androidx.annotation.Keep
import kasem.sm.article.datasource.cache.Quad
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource.utils.DailyReadStatus
import kasem.sm.article.datasource.utils.IsActiveInDailyRead
import kasem.sm.article.datasource.utils.IsBookmarked
import kasem.sm.article.datasource.utils.IsInExplore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ArticleResponse(
    @SerialName("articles")
    val articles: List<ArticleDto>? = null
)

@Keep
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
        quadData: Quad<DailyReadStatus, IsActiveInDailyRead, IsInExplore, IsBookmarked>
    ): ArticleEntity {
        return ArticleEntity(
            id = id,
            title = title,
            description = description,
            topic = topic,
            featuredImage = featuredImage,
            author = author,
            timestamp = timestamp,
            isShownInDailyRead = quadData.first.dailyReadStatus,
            isActiveInDailyRead = quadData.second.isActiveInDailyRead,
            isInExplore = quadData.third.isInExplore,
            isInBookmark = quadData.fourth.isBookmarked
        )
    }
}
