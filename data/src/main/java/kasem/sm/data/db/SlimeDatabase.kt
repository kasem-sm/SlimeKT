/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kasem.sm.article.datasource.cache.dao.ArticleDao
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.topic.datasource.cache.dao.TopicDao
import kasem.sm.topic.datasource.cache.entity.TopicEntity

@Database(
    entities = [
        ArticleEntity::class,
        TopicEntity::class,
    ],
    version = 1,
    // TODO: Add schema path
    exportSchema = false
)
abstract class SlimeDatabase : RoomDatabase() {

    abstract fun topicDao(): TopicDao

    abstract fun articleDao(): ArticleDao
}
