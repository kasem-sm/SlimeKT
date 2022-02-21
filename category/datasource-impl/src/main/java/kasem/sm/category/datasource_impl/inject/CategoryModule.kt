/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.category.datasource_impl.inject

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kasem.sm.category.datasource.cache.CategoryDatabaseService
import kasem.sm.category.datasource.network.CategoryApiService
import kasem.sm.category.datasource_impl.cache.CategoryDatabaseServiceImpl
import kasem.sm.category.datasource_impl.network.CategoryApiServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {
    @Binds
    internal abstract fun bindCategoryCache(bind: CategoryDatabaseServiceImpl): CategoryDatabaseService

    @Binds
    internal abstract fun bindCategoryApi(bind: CategoryApiServiceImpl): CategoryApiService
}
