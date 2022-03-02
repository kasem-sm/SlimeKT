/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource_impl.inject

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.article.datasource.network.ArticleApiService
import kasem.sm.article.datasource_impl.cache.ArticleDatabaseServiceImpl
import kasem.sm.article.datasource_impl.network.ArticleApiServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleModule {

    @Binds
    internal abstract fun bindArticleCache(bind: ArticleDatabaseServiceImpl): ArticleDatabaseService

    @Binds
    internal abstract fun bindArticleApi(bind: ArticleApiServiceImpl): ArticleApiService
}
