package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class AuthorResponse(
    val username: String,
    @BsonId
    val id: String = ObjectId().toString()
)
