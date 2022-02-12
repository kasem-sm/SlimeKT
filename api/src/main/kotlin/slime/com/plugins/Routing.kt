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
import slime.com.routes.registerArticleRoutes
import slime.com.routes.registerAuthenticationRoutes
import slime.com.routes.registerCategoryRoutes
import slime.com.routes.registerSubscribeCategoriesRoute
import slime.com.service.ArticleService
import slime.com.service.UserService
import slime.com.service.SubscriptionService

fun Application.configureRouting() {
    val authRepository by inject<AuthRepository>()
    val articleRepository by inject<ArticleRepository>()
    val categoryRepository by inject<CategoryRepository>()
    val subscribersRepository by inject<SubscribeCategoriesRepository>()

    val userService = UserService(
        authRepository = authRepository,
        jwtDomain = System.getenv("JWT_DO"),
        jwtAudience = System.getenv("JWT_AUD"),
        jwtSecret = System.getenv("JWT_SEC")
    )

    val articleService = ArticleService(articleRepository)
    val subscriptionService = SubscriptionService(
        subscribersRepository, categoryRepository
    )

    routing {
        registerAuthenticationRoutes(userService)
        registerArticleRoutes(articleService)
        registerCategoryRoutes(categoryRepository, subscriptionService)
        registerSubscribeCategoriesRoute(subscriptionService, userService)

        static {
            resources("static")
        }
    }
}
