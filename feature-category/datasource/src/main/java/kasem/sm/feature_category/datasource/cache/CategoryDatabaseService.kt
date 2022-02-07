/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.datasource.cache

import kasem.sm.feature_category.datasource.cache.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryDatabaseService {
    suspend fun insert(category: CategoryEntity)

    suspend fun insert(categories: List<CategoryEntity>)

    fun getAllCategories(): Flow<List<CategoryEntity>>

    fun getSubscribedCategories(): Flow<List<CategoryEntity>>

    fun getCategoriesInExplore(): Flow<List<CategoryEntity>>

    suspend fun isInExplore(id: String): Boolean

    fun getCategoryById(id: String): Flow<CategoryEntity?>

    suspend fun updateSubscriptionStatus(status: Boolean, id: String)
}
