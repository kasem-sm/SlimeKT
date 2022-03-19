/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.datasource_impl.cache.dao.ArticleDao
import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kasem.sm.topic.datasource_impl.cache.dao.TopicDao

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
