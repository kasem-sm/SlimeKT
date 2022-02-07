package slime.com.data.repository.category

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import slime.com.data.models.Category

class CategoryRepositoryImpl(
    db: CoroutineDatabase
) : CategoryRepository {

    private val categoryDb = db.getCollection<Category>()

    override suspend fun getAllCategories(): List<Category> {
        return categoryDb.find().descendingSort(Category::timestamp).toList()
    }

    override suspend fun insertCategory(category: Category): Boolean {
        val exists = categoryDb.findOne(Category::name eq category.name) == null
        return if (exists) {
            categoryDb.insertOne(category).wasAcknowledged()
        } else {
            false
        }
    }

    override suspend fun getCategoryById(categoryId: String): Category? {
        return categoryDb.findOneById(categoryId)
    }
}
