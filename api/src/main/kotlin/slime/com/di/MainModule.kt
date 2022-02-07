package slime.com.di

import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import slime.com.data.repository.article.ArticleRepository
import slime.com.data.repository.article.ArticleRepositoryImpl
import slime.com.data.repository.auth.AuthRepository
import slime.com.data.repository.auth.AuthRepositoryImpl
import slime.com.data.repository.category.CategoryRepository
import slime.com.data.repository.category.CategoryRepositoryImpl
import slime.com.data.repository.subscribed_category.SubscribeCategoriesRepository
import slime.com.data.repository.subscribed_category.SubscribeCategoriesRepositoryImpl
import slime.com.utils.DATABASE_NAME

val mainModule = module(createdAtStart = true) {
    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(DATABASE_NAME)
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
    single<ArticleRepository> {
        ArticleRepositoryImpl(get())
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
    single<SubscribeCategoriesRepository> {
        SubscribeCategoriesRepositoryImpl(get())
    }
}
