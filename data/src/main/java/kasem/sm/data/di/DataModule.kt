/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kasem.sm.article.datasource_impl.inject.ArticleModule
import kasem.sm.authentication.datasource_impl.inject.AuthModule
import kasem.sm.category.datasource_impl.inject.CategoryModule
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.data.db.SlimeDatabase
import kotlinx.coroutines.Dispatchers

@Module(
    includes = [
        AuthModule::class,
        ArticleModule::class,
        CategoryModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object DataModule {

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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "slime_prefs",
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // WorkManager
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideWorkerConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    @Provides
    fun provideSlimeDispatchers(): SlimeDispatchers {
        return SlimeDispatchers(
            default = Dispatchers.Default,
            main = Dispatchers.Main,
            io = Dispatchers.IO
        )
    }
}
