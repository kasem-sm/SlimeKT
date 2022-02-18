/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kasem.sm.article.datasource.cache.dao.ArticleDao
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.category.datasource.cache.dao.CategoryDao
import kasem.sm.category.datasource.cache.entity.CategoryEntity

@Database(
    entities = [
        ArticleEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    // TODO: Add schema path
    exportSchema = false
)
abstract class SlimeDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun articleDao(): ArticleDao
}
