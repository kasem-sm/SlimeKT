/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource_impl.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kasem.sm.core.interfaces.Session
import kasem.sm.core.utils.withResult
import kasem.sm.topic.datasource.network.TopicApiService
import kasem.sm.topic.datasource.network.response.SlimeResponse
import kasem.sm.topic.datasource.network.response.TopicResponse

internal class TopicApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val session: Session
) : TopicApiService {
    override suspend fun getAllTopics(): Result<SlimeResponse<List<TopicResponse>>> {
        return withResult {
            client.get(GET_ALL_TOPICS_ROUTE)
        }
    }

    override suspend fun getTopicById(id: String): Result<SlimeResponse<TopicResponse>> {
        return withResult {
            client.get(GET_TOPIC_BY_ID) {
                parameter("userId", session.getUserId())
                parameter("id", id)
            }
        }
    }

    override suspend fun getSubscribedTopics(): Result<SlimeResponse<List<TopicResponse>>> {
        return withResult {
            client.get(GET_SUBSCRIBED_TOPICS_ROUTE) {
                parameter("userId", session.getUserId())
            }
        }
    }

    override suspend fun getExploreTopics(): Result<SlimeResponse<List<TopicResponse>>> {
        return withResult {
            client.get(GET_EXPLORE_TOPICS_ROUTE) {
                parameter("userId", session.getUserId())
            }
        }
    }

    override suspend fun subscribeIfNot(id: String): Result<SlimeResponse<String>> {
        return withResult {
            client.post(SUBSCRIBE_IF_NOT) {
                contentType(ContentType.Application.Json)
                parameter("topicId", id)
            }
        }
    }

    companion object EndPoints {
        const val GET_ALL_TOPICS_ROUTE = "/api/topic/all"
        const val GET_TOPIC_BY_ID = "/api/topic/get"
        const val GET_SUBSCRIBED_TOPICS_ROUTE = "/api/subscriptionService/all"
        const val SUBSCRIBE_IF_NOT = "/api/subscriptionService/subscribeIfNot"
        const val GET_EXPLORE_TOPICS_ROUTE = "/api/subscriptionService/explore"
    }
}
