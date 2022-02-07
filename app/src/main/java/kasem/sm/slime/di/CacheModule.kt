/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kasem.sm.slime.db.SlimeDatabase

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    internal fun provideArticleDao(db: SlimeDatabase) = db.articleDao()

    @Provides
    @Singleton
    internal fun provideCategoryDao(db: SlimeDatabase) = db.categoryDao()

    @Provides
    @Singleton
    internal fun provideSlimeDatabase(@ApplicationContext context: Context): SlimeDatabase {
        return Room.databaseBuilder(
            context,
            SlimeDatabase::class.java,
            "slime_db"
        ).fallbackToDestructiveMigration().build()
    }
}
