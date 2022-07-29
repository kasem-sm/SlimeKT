/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kasem.sm.article.datasource_impl.inject.ArticleModule
import kasem.sm.authentication.datasource_impl.inject.AuthModule
import kasem.sm.database.db.SlimeDatabase
import kasem.sm.topic.datasource_impl.inject.TopicModule

@Module(
    includes = [
        AuthModule::class,
        ArticleModule::class,
        TopicModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideArticleDao(db: SlimeDatabase) = db.articleDao()

    @Provides
    @Singleton
    internal fun provideTopicDao(db: SlimeDatabase) = db.topicDao()

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
