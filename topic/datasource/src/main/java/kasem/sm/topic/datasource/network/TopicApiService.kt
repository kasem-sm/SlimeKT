/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource.network

import kasem.sm.topic.datasource.network.response.SlimeResponse
import kasem.sm.topic.datasource.network.response.TopicResponse

interface TopicApiService {

    suspend fun getAllTopics(): Result<SlimeResponse<List<TopicResponse>>>

    suspend fun getTopicById(id: String): Result<SlimeResponse<TopicResponse>>

    suspend fun getSubscribedTopics(): Result<SlimeResponse<List<TopicResponse>>>

    suspend fun subscribeIfNot(id: String): Result<SlimeResponse<String>>

    suspend fun getExploreTopics(): Result<SlimeResponse<List<TopicResponse>>>
}
