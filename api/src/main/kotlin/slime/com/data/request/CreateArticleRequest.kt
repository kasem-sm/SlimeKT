package slime.com.data.request

import kotlinx.serialization.Serializable
import slime.com.data.models.Article

@Serializable
data class CreateArticleRequest(
    val title: String,
    val description: String,
    val featuredImage: String,
    val author: String,
    val category: String
) {

    fun toArticle(): Article {
        return Article(
            title = title,
            description = description,
            featuredImage = featuredImage,
            author = author,
            category = category
        )
    }
}
