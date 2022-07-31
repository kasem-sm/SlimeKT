/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.repository.subscribedtopic

import slime.com.data.models.Topic

interface SubscribeTopicsRepository {
    suspend fun getAll(userId: String): List<Topic>
    suspend fun subscribe(userId: String, topicId: String): Boolean
    suspend fun unSubscribe(userId: String, topicId: String): Boolean
    suspend fun checkAlreadySubscribed(userId: String, topicId: String): Boolean
    suspend fun getNumberOfSubscribers(topicId: String): Int
}
