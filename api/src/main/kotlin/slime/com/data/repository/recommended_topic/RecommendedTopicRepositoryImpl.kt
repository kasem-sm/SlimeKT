/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.recommended_topic

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import slime.com.data.models.RecommendedTopic

class RecommendedTopicRepositoryImpl(
    db: CoroutineDatabase,
) : RecommendedTopicRepository {

    private val recommendedDb = db.getCollection<RecommendedTopic>()

    override suspend fun insertRecommendedTopic(userId: String, topicId: String, topicName: String): Boolean {
        return try {
            recommendedDb.insertOne(
                RecommendedTopic(
                    userId = userId,
                    topicId = topicId,
                    topicName = topicName
                )
            ).wasAcknowledged()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getRecommendedTopic(currentUserId: String): RecommendedTopic? {
        return recommendedDb.find(RecommendedTopic::userId eq currentUserId).toList().firstOrNull()
    }
}
