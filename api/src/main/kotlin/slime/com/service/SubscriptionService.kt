package slime.com.service

import slime.com.data.models.Category
import slime.com.data.repository.category.CategoryRepository
import slime.com.data.repository.subscribed_category.SubscribeCategoriesRepository
import slime.com.utils.ServiceResult

class SubscriptionService(
    private val subscribeRepository: SubscribeCategoriesRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun getNumber(categoryId: String) = subscribeRepository.getNumberOfSubscribers(categoryId)

    suspend fun verifyAndSubscribe(userId: String, categoryId: String): ServiceResult {
        return when {
            categoryRepository.getCategoryById(categoryId) == null -> ServiceResult.Error("Doesn't Exists")
            // technically this should not happen but for a safer side
            checkIfUserSubscribes(userId, categoryId) -> ServiceResult.Error("Already Subscribed")
            else -> subscribe(userId, categoryId)
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

    suspend fun verifyAndUnsubscribe(userId: String, categoryId: String): ServiceResult {
        return when {
            categoryRepository.getCategoryById(categoryId) == null -> ServiceResult.Error("Doesn't Exists")
            // technically this should not happen but for a safer side
            checkIfUserSubscribes(userId, categoryId) -> ServiceResult.Error("You are trying to unsubscribe from a category you don't subscribe")
            else -> unsubscribe(userId, categoryId)
        }
    }

    private suspend fun unsubscribe(userId: String, categoryId: String): ServiceResult {
        subscribeRepository.unSubscribe(userId, categoryId).let { transaction ->
            return when (transaction) {
                true -> ServiceResult.Success("Unsubscribed")
                false -> ServiceResult.Error("Failed")
            }
        }
    }

    suspend fun getUserSubscribedCategories(userId: String): List<Category> {
        return subscribeRepository.getAll(userId).map {
            val totalSubscribers = getNumber(it.id)
            it.copy(totalSubscribers = totalSubscribers, hasUserSubscribed = true)
        }
    }

    suspend fun checkIfUserSubscribes(userId: String, categoryId: String): Boolean {
        return when {
            subscribeRepository.checkAlreadySubscribed(
                userId = userId, categoryId = categoryId
            ) -> true
            else -> false
        }
    }

    suspend fun getCategoriesNotSubscribed(currentUserId: String): List<Category> {
        return categoryRepository.getAllCategories().filter {
            !checkIfUserSubscribes(currentUserId, it.id)
        }.toList().map {
            val totalSubscribers = getNumber(it.id)
            it.copy(totalSubscribers = totalSubscribers, hasUserSubscribed = false)
        }
    }
}
