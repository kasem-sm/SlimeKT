package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.models.Topic
import slime.com.data.repository.topic.TopicRepository
import slime.com.data.response.SlimeResponse
import slime.com.service.SubscriptionService
import slime.com.utils.respondWith

fun Route.registerTopicRoutes(
    repository: TopicRepository,
    subscriptionService: SubscriptionService
) {
    get("/api/topic/all") {
        val topics = repository.getAllTopics().map {
            val totalSubscribers = subscriptionService.getNumber(it.id)
            it.copy(totalSubscribers = totalSubscribers)
        }
        respondWith(topics)
    }

    get("/api/topic/get") {
        val topicId = call.parameters["id"] ?: return@get
        val userId = call.parameters["userId"]

        val topic = repository.getTopicById(topicId)?.let {
            if (userId != null) {
                val totalSubscribers = subscriptionService.getNumber(it.id)
                val hasUserSubscribed = subscriptionService.checkIfUserSubscribes(userId, topicId)
                it.copy(totalSubscribers = totalSubscribers, hasUserSubscribed = hasUserSubscribed)
            } else {
                val totalSubscribers = subscriptionService.getNumber(it.id)
                it.copy(totalSubscribers = totalSubscribers)
            }
        }

        respondWith(topic)
    }

    authenticate {
        post("/api/topic/create") {
            val topicName = call.parameters["name"] ?: return@post

            when (repository.insertTopic(Topic(name = topicName.trim()))) {
                true -> respondWith<Unit>(SlimeResponse(true, "Topic created successfully"))
                false -> respondWith<Unit>(
                    SlimeResponse(
                        false,
                        "Failed to create new topic, please try again later"
                    )
                )
            }
        }
    }
}
