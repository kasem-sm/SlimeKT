package slime.com.data.repository.topic

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import slime.com.data.models.Topic

class TopicRepositoryImpl(
    db: CoroutineDatabase
) : TopicRepository {

    private val topicDb = db.getCollection<Topic>()

    override suspend fun getAllTopics(): List<Topic> {
        return topicDb.find().descendingSort(Topic::timestamp).toList()
    }

    override suspend fun insertTopic(topic: Topic): Boolean {
        val exists = topicDb.findOne(Topic::name eq topic.name) == null
        return if (exists) {
            topicDb.insertOne(topic).wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun getTopicById(topicId: String): Topic? {
        return topicDb.findOneById(topicId)
    }
}
