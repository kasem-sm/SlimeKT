package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.plugins.userId
import slime.com.service.SubscriptionService
import slime.com.service.UserService
import slime.com.utils.getUserId
import slime.com.utils.respondWith
import slime.com.utils.respondWithResult

fun Route.registerSubscribeCategoriesRoute(
    service: SubscriptionService,
    userService: UserService
) {
    authenticate {
        post("/api/subscribedCategories/subscribe") {
            getUserId(userService) { userId ->
                respondWithResult {
                    service.verifyAndSubscribe(userId, call.parameters["id"] ?: return@post)
                }
            }
        }
    }

    get("api/subscribedCategories/all") {
        val userId = call.userId
        respondWith(service.getUserSubscribedCategories(userId))
    }

    authenticate {
        post("/api/subscribedCategories/unsubscribe") {
            getUserId(userService) { userId ->
                respondWithResult {
                    service.verifyAndUnsubscribe(userId, call.parameters["id"] ?: return@post)
                }
            }
        }
    }

    authenticate {
        get("/api/subscribedCategories/verify") {
            getUserId(userService) { userId ->
                val data = service.checkIfUserSubscribes(userId, call.parameters["id"] ?: return@get)
                respondWith(data)
            }
        }
    }

    get("api/subscribedCategories/explore") {
        val userId = call.userId
        respondWith(service.getCategoriesNotSubscribed(userId))
    }

    get("api/subscribedCategories/totalSubscribers") {
        respondWith(service.getNumber(call.parameters["id"] ?: return@get))
    }
}
