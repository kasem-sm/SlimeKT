package slime.com.data.repository.subscribed_category

import slime.com.data.models.Category

interface SubscribeCategoriesRepository {
    suspend fun getAll(userId: String): List<Category>
    suspend fun subscribe(userId: String, categoryId: String): Boolean
    suspend fun unSubscribe(userId: String, categoryId: String): Boolean
    suspend fun checkAlreadySubscribed(userId: String, categoryId: String): Boolean
    suspend fun getNumberOfSubscribers(categoryId: String): Int
}
