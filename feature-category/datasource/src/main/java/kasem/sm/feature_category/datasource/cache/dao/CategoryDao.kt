/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kasem.sm.feature_category.datasource.cache.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<CategoryEntity>)

    @Query("SELECT * FROM table_category ORDER BY category_timestamp DESC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM table_category WHERE is_in_subscription = 1")
    fun getSubscribedCategories(): Flow<List<CategoryEntity>>

    // TODO ??
    @Query("SELECT * FROM table_category WHERE is_in_subscription = 1")
    fun getSubscribedCategoriesNonFlow(): List<CategoryEntity>

    @Query("SELECT * FROM table_category WHERE is_in_explore = 1")
    fun getCategoriesInExplore(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM table_category WHERE is_in_explore = 1")
    suspend fun getCategoriesInExploreNonFlow(): List<CategoryEntity>

    @Query("SELECT * FROM table_category WHERE category_id = :id")
    fun getCategoryById(id: String): Flow<CategoryEntity?>

    @Query("UPDATE table_category SET is_in_subscription = :inSubscription, is_in_explore = :inExplore WHERE category_id = :id")
    suspend fun updateSubscriptionStatus(
        inSubscription: Boolean,
        inExplore: Boolean,
        id: String
    )

    @Query("UPDATE table_category SET is_in_subscription = 0, is_in_explore = 1")
    suspend fun clearSubscription()
}
