package slime.com.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class SubscribedTopic(
    val userId: String,
    val topicId: String,
    @BsonId
    val id: String = ObjectId().toString(),
)
