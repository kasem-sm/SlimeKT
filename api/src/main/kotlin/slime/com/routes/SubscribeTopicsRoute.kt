/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.routes

import io.ktor.auth.authenticate
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.models.Topic
import slime.com.data.repository.topic.TopicRepository
import slime.com.service.SubscriptionService
import slime.com.service.UserService
import slime.com.utils.get
import slime.com.utils.getUserId
import slime.com.utils.respondWith
import slime.com.utils.respondWithResult

fun Route.subscribeTopicsRoute(
    service: SubscriptionService,
    userService: UserService,
    topicRepository: TopicRepository
) {
    authenticate {
        post("/api/subscriptionService/subscribeIfNot") {
            val topicId = get("topicId") ?: return@post
            getUserId(userService) { userId ->
                val isSubscribed = service.checkIfUserSubscribes(userId, topicId)
                respondWithResult(data = topicId) {
                    if (isSubscribed) {
                        service.verifyAndUnsubscribe(userId, topicId)
                    } else service.verifyAndSubscribe(userId, topicId)
                }
            }
        }
    }

    get("api/subscriptionService/all") {
        val userId = getUserId()
        if (userId != null) {
            respondWith(service.getUserSubscribedTopics(userId))
        } else respondWith(emptyList<Topic>())
    }

    get("api/subscriptionService/explore") {
        val userId = getUserId()
        if (userId != null) {
            respondWith(service.getTopicsNotSubscribed(userId))
        } else respondWith(topicRepository.getAllTopics())
    }
}
