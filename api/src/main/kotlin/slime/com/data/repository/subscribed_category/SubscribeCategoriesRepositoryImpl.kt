package slime.com.data.repository.subscribed_category

import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import slime.com.data.models.Category
import slime.com.data.models.SubscribedCategory

class SubscribeCategoriesRepositoryImpl(
    db: CoroutineDatabase
) : SubscribeCategoriesRepository {

    private val subscribedCategoryDb = db.getCollection<SubscribedCategory>()
    private val categoryDb = db.getCollection<Category>()

    override suspend fun getAll(userId: String): List<Category> {
        val list = subscribedCategoryDb.find(SubscribedCategory::userId eq userId).toList().map {
            categoryDb.findOneById(it.categoryId) ?: return listOf()
        }
        return list
    }

    override suspend fun subscribe(userId: String, categoryId: String): Boolean {
        return subscribedCategoryDb.insertOne(
            SubscribedCategory(
                userId = userId, categoryId = categoryId
            )
        ).wasAcknowledged()
    }

    override suspend fun unSubscribe(userId: String, categoryId: String): Boolean {
        return subscribedCategoryDb.deleteOne(
            and(
                SubscribedCategory::userId eq userId, SubscribedCategory::categoryId eq categoryId
            )
        ).wasAcknowledged()
    }

    override suspend fun checkAlreadySubscribed(userId: String, categoryId: String): Boolean {
        return subscribedCategoryDb.findOne(
            and(
                SubscribedCategory::userId eq userId, SubscribedCategory::categoryId eq categoryId
            )
        ) != null
    }

    override suspend fun getNumberOfSubscribers(categoryId: String): Int {
        return subscribedCategoryDb.find(SubscribedCategory::categoryId eq categoryId).toList().count()
    }
}
