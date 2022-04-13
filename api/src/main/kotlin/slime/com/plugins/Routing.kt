/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.plugins

import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing
import org.koin.ktor.ext.inject
import slime.com.data.repository.article.ArticleRepository
import slime.com.data.repository.auth.AuthRepository
import slime.com.data.repository.recommended_topic.RecommendedTopicRepository
import slime.com.data.repository.topic.TopicRepository
import slime.com.isDebugMode
import slime.com.routes.registerArticleRoutes
import slime.com.routes.registerAuthenticationRoutes
import slime.com.routes.registerSubscribeTopicsRoute
import slime.com.routes.registerTopicRoutes
import slime.com.service.ArticleService
import slime.com.service.SubscriptionService
import slime.com.service.UserService

fun Application.configureRouting() {
    val authRepository by inject<AuthRepository>()
    val articleRepository by inject<ArticleRepository>()
    val topicRepository by inject<TopicRepository>()
    val recommendedTopicRepository by inject<RecommendedTopicRepository>()

    val jwtAudience = if (isDebugMode) "jwt_aud" else System.getenv("JWT_AUD")
    val jwtDomain = if (isDebugMode) "jwt_dom" else System.getenv("JWT_DO")
    val jwtSec = if (isDebugMode) "secret" else System.getenv("JWT_SEC")

    val userService = UserService(
        authRepository = authRepository,
        jwtDomain = jwtDomain,
        jwtAudience = jwtAudience,
        jwtSecret = jwtSec
    )

    val articleService = ArticleService(articleRepository, recommendedTopicRepository)
    val subscriptionService by inject<SubscriptionService>()

    routing {
        registerAuthenticationRoutes(userService)
        registerArticleRoutes(articleService)
        registerTopicRoutes(topicRepository, subscriptionService)
        registerSubscribeTopicsRoute(subscriptionService, userService, topicRepository)

        static {
            resources("static")
        }
    }
}
