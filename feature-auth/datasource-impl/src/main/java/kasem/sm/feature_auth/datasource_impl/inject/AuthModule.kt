/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_auth.datasource_impl.inject

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import kasem.sm.feature_auth.datasource.network.AuthApiService
import kasem.sm.feature_auth.datasource_impl.network.AuthApiServiceImpl

@Module
@DisableInstallInCheck
abstract class AuthModule {

    @Binds
    internal abstract fun bindAuthApi(service: AuthApiServiceImpl): AuthApiService
}
