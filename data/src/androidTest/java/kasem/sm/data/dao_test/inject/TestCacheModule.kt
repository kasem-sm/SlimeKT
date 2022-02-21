/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.dao_test.inject

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import kasem.sm.data.db.SlimeDatabase

@Module
@InstallIn(SingletonComponent::class)
object TestCacheModule {

    @Provides
    @Named("slime_test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): SlimeDatabase {
        return Room.inMemoryDatabaseBuilder(
            context, SlimeDatabase::class.java
        ).allowMainThreadQueries().build()
    }
}
