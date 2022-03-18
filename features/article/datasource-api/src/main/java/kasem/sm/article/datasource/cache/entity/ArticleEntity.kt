/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_article")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val topic: String,
    @ColumnInfo(name = "featured_image")
    val featuredImage: String,
    val author: String,
    val timestamp: Long,
    @ColumnInfo(name = "is_shown")
    val isShownInDailyRead: Boolean,
    @ColumnInfo(name = "is_active")
    val isActiveInDailyRead: Boolean,
    @ColumnInfo(name = "is_in_explore")
    val isInExplore: Boolean,
)
