/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.auth_impl.di

import com.slime.auth_api.AuthManager
import com.slime.auth_impl.AuthManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    internal abstract fun bindAuthState(bind: AuthManagerImpl): AuthManager
}
