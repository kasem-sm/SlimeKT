package slime.com.data.repository.category

import slime.com.data.models.Category

interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun insertCategory(category: Category): Boolean
    suspend fun getCategoryById(categoryId: String): Category?
}
