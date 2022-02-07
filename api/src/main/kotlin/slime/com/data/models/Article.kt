package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Article(
    val title: String,
    val description: String,
    val featuredImage: String,
    val author: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis(),
    @BsonId
    val id: String = ObjectId().toString(),
)
