package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.models.Category
import slime.com.data.repository.category.CategoryRepository
import slime.com.service.SubscriptionService
import slime.com.service.UserService
import slime.com.utils.getUserId
import slime.com.utils.respondWith
import slime.com.utils.respondWithResult

fun Route.registerSubscribeCategoriesRoute(
    service: SubscriptionService,
    userService: UserService,
    categoryRepository: CategoryRepository
) {
    authenticate {
        post("/api/subscriptionService/subscribeIfNot") {
            val categoryId = call.parameters["categoryId"] ?: return@post
            getUserId(userService) { userId ->
                val isSubscribed = service.checkIfUserSubscribes(userId, categoryId)
                respondWithResult(data = categoryId) {
                    if (isSubscribed) {
                        service.verifyAndUnsubscribe(userId, categoryId)
                    } else service.verifyAndSubscribe(userId, categoryId)
                }
            }
        }
    }

    get("api/subscriptionService/all") {
        val userId = call.parameters["userId"]
        if (userId != null) {
            respondWith(service.getUserSubscribedCategories(userId))
        } else respondWith(emptyList<Category>())
    }

    get("api/subscriptionService/explore") {
        val userId = call.parameters["userId"]
        if (userId != null) {
            respondWith(service.getCategoriesNotSubscribed(userId))
        } else respondWith(categoryRepository.getAllCategories())
    }
}