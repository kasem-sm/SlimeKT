/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.recommended_topic

import slime.com.data.models.RecommendedTopic

interface RecommendedTopicRepository {
    suspend fun getRecommendedTopic(currentUserId: String): RecommendedTopic?
    suspend fun insertRecommendedTopic(userId: String, topicId: String, topicName: String): Boolean
}
