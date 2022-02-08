package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receiveOrNull
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.models.Category
import slime.com.data.repository.category.CategoryRepository
import slime.com.data.request.CreateCategoryRequest
import slime.com.data.response.SlimeResponse
import slime.com.service.SubscriptionService
import slime.com.utils.respondWith
import slime.com.utils.respondWithBadRequest

fun Route.registerCategoryRoutes(
    repository: CategoryRepository,
    subscriptionService: SubscriptionService
) {
    get("/api/category/all") {
        val categories = repository.getAllCategories().map {
            val totalSubscribers = subscriptionService.getNumber(it.id)
            it.copy(totalSubscribers = totalSubscribers)
        }
        respondWith(categories)
    }

    get("/api/category/get") {
        val id = call.parameters["id"] ?: return@get
        val category = repository.getCategoryById(id)?.let {
            val totalSubscribers = subscriptionService.getNumber(it.id)
            it.copy(totalSubscribers = totalSubscribers)
        } ?: return@get
        respondWith(category)
    }

    authenticate {
        post("/api/category/create") {
            val response = call.receiveOrNull<CreateCategoryRequest>() ?: kotlin.run {
                respondWithBadRequest()
                return@post
            }

            when (repository.insertCategory(Category(name = response.name.trim()))) {
                true -> respondWith<Unit>(SlimeResponse(true, "Category created successfully"))
                false -> respondWith<Unit>(SlimeResponse(false, "Failed to create new category, please try again later"))
            }
        }
    }
}
