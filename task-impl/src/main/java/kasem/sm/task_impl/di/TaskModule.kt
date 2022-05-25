/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.task_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kasem.sm.task_api.Tasks
import kasem.sm.task_impl.TaskImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    internal abstract fun bindTask(bind: TaskImpl): Tasks
}
