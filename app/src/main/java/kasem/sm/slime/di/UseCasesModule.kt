/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kasem.sm.authentication.domain.usecases.ValidatePassword
import kasem.sm.authentication.domain.usecases.ValidateUsername

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideValidateUsernameUseCase(application: Application): ValidateUsername = ValidateUsername(application)

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(application: Application): ValidatePassword = ValidatePassword(application)
}
