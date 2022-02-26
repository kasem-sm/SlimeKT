/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.di

import dagger.Binds
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
import kasem.sm.core.interfaces.AuthManager
import kasem.sm.core.interfaces.Tasks
import kasem.sm.data.session.AuthManagerImpl
import kasem.sm.data.tasks.TaskImpl
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    internal abstract fun bindAuthState(bind: AuthManagerImpl): AuthManager

    @Binds
    internal abstract fun bindTask(bind: TaskImpl): Tasks

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(
            authManager: AuthManager,
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

                    authManager.getUserToken()?.let { token ->
                        header("Authorization", "Bearer $token")
                    }
                }
            }
        }

        private const val BASE_URL = "slime-kt.herokuapp.com"
        private const val LOCAL_BASE_URL = "192.168.0.106"
    }
}
