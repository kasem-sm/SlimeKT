package slime.com.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receiveOrNull
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import slime.com.data.request.CreateArticleRequest
import slime.com.data.response.Info
import slime.com.data.response.PagedArticlesResponse
import slime.com.data.response.SlimeResponse
import slime.com.service.ArticleService
import slime.com.utils.respondWith
import slime.com.utils.respondWithBadRequest
import slime.com.utils.respondWithResult
import kotlin.math.ceil

fun Route.registerArticleRoutes(
    service: ArticleService
) {
    get("/api/article/get/random") {
        val userId = call.parameters["userId"]
        // Return an article from subscription if the user is authorized,
        // if not return any random article
        val article = service.getRandomArticleFromUsersSubscription(userId)
        respondWith(article)
    }

    get("/api/article/get") {
        val article = service.getArticleById(
            articleId = call.parameters["id"]?.toInt() ?: return@get
        ) ?: return@get
        respondWith(article)
    }

    get("/api/article/all") {
        val topic = call.parameters["topic"] ?: ""
        val query = call.parameters["query"] ?: ""
        val page = call.parameters["page"]?.toIntOrNull() ?: 0
        val pageSize = call.parameters["pageSize"]?.toIntOrNull() ?: 3

        val articlesAndSize = service.getAllArticles(
            topic = topic, query = query, page = page, pageSize = pageSize
        )

        val totalArticlesCount = articlesAndSize.second
        val totalPages = ceil((totalArticlesCount.toDouble() / pageSize.toDouble()))

        val pagedArticlesResponse = PagedArticlesResponse(
            info = Info(
                articleSize = totalArticlesCount,
                totalPages = totalPages.toInt() - 1,
                prevPage = if (page == 0) null else page - 1,
                nextPage = if (page >= totalPages.toInt() - 1) null else page + 1,
            ),
            articles = articlesAndSize.first
        )
        respondWith(pagedArticlesResponse)
    }

    authenticate {
        post("/api/article/create") {
            val response = call.receiveOrNull<CreateArticleRequest>() ?: kotlin.run {
                respondWithBadRequest()
                return@post
            }

            respondWithResult {
                service.validateAndCreateArticle(response)
            }
        }
    }

    authenticate {
        delete("/api/article/delete") {
            val result = service.deleteArticleById(
                articleId = call.parameters["id"]?.toInt() ?: return@delete
            )
            when (result) {
                true -> respondWith<Unit>(SlimeResponse(true, "Article deleted successfully"))
                false -> respondWith<Unit>(
                    SlimeResponse(
                        false, "Couldn't complete your request, Please try again later"
                    )
                )
            }
        }
    }

    get("/api/article/explore") {
        val userId = call.parameters["userId"]
        val articlesInExplore = service.getArticlesInExplore(userId)
        respondWith(articlesInExplore)
    }
}
