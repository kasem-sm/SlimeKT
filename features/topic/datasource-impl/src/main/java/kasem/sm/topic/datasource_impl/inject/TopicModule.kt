/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource_impl.inject

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.network.TopicApiService
import kasem.sm.topic.datasource_impl.cache.TopicDatabaseServiceImpl
import kasem.sm.topic.datasource_impl.network.TopicApiServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TopicModule {
    @Binds
    internal abstract fun bindTopicCache(bind: TopicDatabaseServiceImpl): TopicDatabaseService

    @Binds
    internal abstract fun bindTopicApi(bind: TopicApiServiceImpl): TopicApiService
}
