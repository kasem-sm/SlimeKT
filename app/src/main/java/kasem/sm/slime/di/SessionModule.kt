/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kasem.sm.core.interfaces.Session
import kasem.sm.slime.session.SessionImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    internal abstract fun bindSession(bind: SessionImpl): Session
}
