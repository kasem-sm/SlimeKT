/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.datasource.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_category")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val id: String,
    @ColumnInfo(name = "category_title")
    val title: String,
    @ColumnInfo(name = "category_timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "is_in_subscription")
    val isInSubscription: Boolean = false,
    @ColumnInfo(name = "is_in_explore")
    val isInExplore: Boolean = false,
    @ColumnInfo(name = "total_subscribers")
    val totalSubscribers: Int,
)
