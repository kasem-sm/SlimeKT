/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.topic

import slime.com.data.models.Topic

interface TopicRepository {
    suspend fun getAllTopics(): List<Topic>
    suspend fun insertTopic(topic: Topic): Boolean
    suspend fun getTopicById(topicId: String): Topic?
}
