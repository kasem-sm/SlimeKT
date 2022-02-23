/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_topic")
data class TopicEntity(
    @PrimaryKey
    @ColumnInfo(name = "topic_id")
    val id: String,
    @ColumnInfo(name = "topic_title")
    val title: String,
    @ColumnInfo(name = "topic_timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "is_in_subscription")
    val isInSubscription: Boolean = false,
    @ColumnInfo(name = "is_in_explore")
    val isInExplore: Boolean = false,
    @ColumnInfo(name = "total_subscribers")
    val totalSubscribers: Int,
)
