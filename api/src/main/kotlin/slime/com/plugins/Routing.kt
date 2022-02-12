package slime.com.plugins

import io.ktor.application.Application
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing
import org.koin.ktor.ext.inject
import slime.com.data.repository.article.ArticleRepository
import slime.com.data.repository.auth.AuthRepository
import slime.com.data.repository.category.CategoryRepository
import slime.com.data.repository.subscribed_category.SubscribeCategoriesRepository
import slime.com.isDebugMode
import slime.com.routes.registerArticleRoutes
import slime.com.routes.registerAuthenticationRoutes
import slime.com.routes.registerCategoryRoutes
import slime.com.routes.registerSubscribeCategoriesRoute
import slime.com.service.ArticleService
import slime.com.service.SubscriptionService
import slime.com.service.UserService

fun Application.configureRouting() {
    val authRepository by inject<AuthRepository>()
    val articleRepository by inject<ArticleRepository>()
    val categoryRepository by inject<CategoryRepository>()
    val subscribersRepository by inject<SubscribeCategoriesRepository>()

    val jwtAudience = if (isDebugMode) "jwt_aud" else System.getenv("JWT_AUD")
    val jwtDomain = if (isDebugMode) "jwt_dom" else System.getenv("JWT_DO")
    val jwtSec = if (isDebugMode) "secret" else System.getenv("JWT_SEC")

    val userService = UserService(
        authRepository = authRepository,
        jwtDomain = jwtDomain,
        jwtAudience = jwtAudience,
        jwtSecret = jwtSec
    )

    val articleService = ArticleService(articleRepository)
    val subscriptionService = SubscriptionService(
        subscribersRepository, categoryRepository
    )

    routing {
        registerAuthenticationRoutes(userService)
        registerArticleRoutes(articleService)
        registerCategoryRoutes(categoryRepository, subscriptionService)
        registerSubscribeCategoriesRoute(subscriptionService, userService, categoryRepository)

        static {
            resources("static")
        }
    }
}
