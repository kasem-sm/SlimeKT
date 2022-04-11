/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.subscribed_topic

import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import slime.com.data.models.SubscribedTopic
import slime.com.data.models.Topic

class SubscribeTopicsRepositoryImpl(
    db: CoroutineDatabase,
) : SubscribeTopicsRepository {

    private val subscribedTopicDb = db.getCollection<SubscribedTopic>()
    private val topicDb = db.getCollection<Topic>()

    override suspend fun getAll(userId: String): List<Topic> {
        return subscribedTopicDb.find(SubscribedTopic::userId eq userId).toList().map {
            topicDb.findOneById(it.topicId) ?: throw Exception("How?")
        }
    }

    override suspend fun subscribe(userId: String, topicId: String): Boolean {
        return subscribedTopicDb.insertOne(
            SubscribedTopic(
                userId = userId, topicId = topicId
            )
        ).wasAcknowledged()
    }

    override suspend fun unSubscribe(userId: String, topicId: String): Boolean {
        return subscribedTopicDb.deleteOne(
            and(
                SubscribedTopic::userId eq userId, SubscribedTopic::topicId eq topicId
            )
        ).wasAcknowledged()
    }

    override suspend fun checkAlreadySubscribed(userId: String, topicId: String): Boolean {
        return subscribedTopicDb.findOne(
            and(
                SubscribedTopic::userId eq userId, SubscribedTopic::topicId eq topicId
            )
        ) != null
    }

    override suspend fun getNumberOfSubscribers(topicId: String): Int {
        return subscribedTopicDb.find(SubscribedTopic::topicId eq topicId).toList().count()
    }
}
