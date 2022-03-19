/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource_impl.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topic: TopicEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topics: List<TopicEntity>)

    @Query("SELECT * FROM table_topic ORDER BY topic_title ASC")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM table_topic ORDER BY topic_title ASC")
    suspend fun getAllTopicsNonFlow(): List<TopicEntity>

    @Query("SELECT * FROM table_topic WHERE is_in_subscription = 1 ORDER BY topic_title ASC")
    fun getSubscribedTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM table_topic WHERE is_in_explore = 1 ORDER BY topic_title ASC")
    fun getTopicsInExplore(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM table_topic WHERE is_in_explore = 1 ORDER BY topic_title ASC")
    suspend fun getTopicsInExploreNonFlow(): List<TopicEntity>

    @Query("SELECT * FROM table_topic WHERE topic_id = :id")
    fun getTopicById(id: String): Flow<TopicEntity?>

    @Query("UPDATE table_topic SET is_in_subscription = :inSubscription, is_in_explore = :inExplore WHERE topic_id = :id")
    suspend fun updateSubscriptionStatus(
        inSubscription: Boolean,
        inExplore: Boolean,
        id: String
    )

    @Query("UPDATE table_topic SET is_in_subscription = 0, is_in_explore = 1")
    suspend fun removeAllTopicsFromSubscription()
}
