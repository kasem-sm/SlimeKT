/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource.cache

import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

interface TopicDatabaseService {
    suspend fun insert(topic: TopicEntity)

    suspend fun insert(topics: List<TopicEntity>)

    fun getAllTopics(): Flow<List<TopicEntity>>

    suspend fun getAllTopicsNonFlow(): List<TopicEntity>

    fun getSubscribedTopics(): Flow<List<TopicEntity>>

    fun getTopicsInExplore(): Flow<List<TopicEntity>>

    suspend fun isInExplore(id: String): Boolean

    fun getTopicById(id: String): Flow<TopicEntity?>

    fun getTopicByTitle(title: String): Flow<TopicEntity?>

    suspend fun updateSubscriptionStatus(status: Boolean, id: String? = null)
}
