package slime.com.data.response

import kotlinx.serialization.Serializable
import slime.com.data.models.Article

@Serializable
data class PagedArticlesResponse(
    val info: Info? = null,
    val articles: List<Article>? = null
)

@Serializable
data class Info(
    val articleSize: Int,
    val totalPages: Int,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
)
