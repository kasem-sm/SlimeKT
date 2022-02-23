package slime.com.service

import slime.com.data.models.Topic
import slime.com.data.repository.subscribed_topic.SubscribeTopicsRepository
import slime.com.data.repository.topic.TopicRepository
import slime.com.utils.ServiceResult

class SubscriptionService(
    private val subscribeRepository: SubscribeTopicsRepository,
    private val topicRepository: TopicRepository
) {

    suspend fun getNumber(topicId: String) = subscribeRepository.getNumberOfSubscribers(topicId)

    suspend fun verifyAndSubscribe(userId: String, topicId: String): ServiceResult {
        return when {
            topicRepository.getTopicById(topicId) == null -> ServiceResult.Error("Doesn't Exists")
            // technically this should not happen but for a safer side
            checkIfUserSubscribes(userId, topicId) -> ServiceResult.Error("Already Subscribed")
            else -> subscribe(userId, topicId)
        }
    }

    private suspend fun subscribe(userId: String, followingUserId: String): ServiceResult {
        subscribeRepository.subscribe(userId, followingUserId).let { transaction ->
            return when (transaction) {
                true -> ServiceResult.Success("Subscribed")
                false -> ServiceResult.Error("Failed")
            }
        }
    }

    suspend fun verifyAndUnsubscribe(userId: String, topicId: String): ServiceResult {
        return when {
            topicRepository.getTopicById(topicId) == null -> ServiceResult.Error("Doesn't Exists")
            // technically this should not happen but for a safer side
            !checkIfUserSubscribes(userId, topicId) -> ServiceResult.Error("You are trying to unsubscribe from a topic you don't subscribe")
            else -> unsubscribe(userId, topicId)
        }
    }

    private suspend fun unsubscribe(userId: String, topicId: String): ServiceResult {
        subscribeRepository.unSubscribe(userId, topicId).let { transaction ->
            return when (transaction) {
                true -> ServiceResult.Success("Unsubscribed")
                false -> ServiceResult.Error("Failed")
            }
        }
    }

    suspend fun getUserSubscribedTopics(userId: String): List<Topic> {
        return subscribeRepository.getAll(userId).map {
            val totalSubscribers = getNumber(it.id)
            it.copy(totalSubscribers = totalSubscribers, hasUserSubscribed = true)
        }
    }

    suspend fun checkIfUserSubscribes(userId: String, topicId: String): Boolean {
        return when {
            subscribeRepository.checkAlreadySubscribed(
                userId = userId, topicId = topicId
            ) -> true
            else -> false
        }
    }

    suspend fun getTopicsNotSubscribed(currentUserId: String): List<Topic> {
        return topicRepository.getAllTopics().filter {
            !checkIfUserSubscribes(currentUserId, it.id)
        }.toList().map {
            val totalSubscribers = getNumber(it.id)
            // This should always be false except when a user sign in into our app
            // and then directly visits Explore section where explore topics should be filtered
            // with any previously subscribed topics and the rest (unsubscribed) topics should be returned
            // as a response.
            val hasUserSubscribed = checkIfUserSubscribes(userId = currentUserId, it.id)
            it.copy(totalSubscribers = totalSubscribers, hasUserSubscribed = hasUserSubscribed)
        }
    }
}
