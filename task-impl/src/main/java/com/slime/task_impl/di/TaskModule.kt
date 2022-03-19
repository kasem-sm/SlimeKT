/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.task_impl.di

import com.slime.task_api.Tasks
import com.slime.task_impl.TaskImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    internal abstract fun bindTask(bind: TaskImpl): Tasks
}
