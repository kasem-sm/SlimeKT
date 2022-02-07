package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    val username: String,
    var password: String,
    @BsonId
    val id: String = ObjectId().toString()
)
