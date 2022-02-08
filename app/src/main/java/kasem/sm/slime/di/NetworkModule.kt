/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.slime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.client.request.host
import io.ktor.client.request.port
import io.ktor.http.URLProtocol
import javax.inject.Singleton
import kasem.sm.core.interfaces.Session
import kasem.sm.feature_article.datasource_impl.inject.ArticleModule
import kasem.sm.feature_auth.datasource_impl.inject.AuthModule
import kasem.sm.feature_category.datasource_impl.inject.CategoryModule
import kotlinx.serialization.json.Json

@Module(
    includes = [
        AuthModule::class,
        ArticleModule::class,
        CategoryModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        session: Session,
    ): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                host = BASE_URL
                when (host) {
                    BASE_URL -> url { protocol = URLProtocol.HTTPS }
                    else -> {
                        url { protocol = URLProtocol.HTTP }
                        port = 8000
                    }
                }

                session.fetchToken()?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }
        }
    }

    private const val BASE_URL = "slime-kt.herokuapp.com"
    private const val LOCAL_BASE_URL = "192.168.0.106"
}
