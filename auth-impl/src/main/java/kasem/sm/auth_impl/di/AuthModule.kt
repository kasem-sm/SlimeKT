/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.auth_impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kasem.sm.auth_api.AuthManager
import kasem.sm.auth_impl.AuthManagerImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    internal abstract fun bindAuthState(bind: AuthManagerImpl): AuthManager
}
