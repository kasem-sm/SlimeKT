package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Author(
    val name: String,
    val profilePicture: String,
    @BsonId
    val id: String = ObjectId().toString(),
)
