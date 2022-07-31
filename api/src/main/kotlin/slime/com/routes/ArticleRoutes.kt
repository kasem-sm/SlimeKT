/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.routes

import io.ktor.auth.authenticate
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import slime.com.data.models.Article
import slime.com.data.response.ArticlesResponse
import slime.com.data.response.SlimeResponse
import slime.com.service.ArticleService
import slime.com.utils.get
import slime.com.utils.getUserId
import slime.com.utils.respondWith

fun Route.articleRoutes(
    service: ArticleService
) {
    get("/api/article/get/random") {
        val userId = getUserId()
        // Return an article from subscription if the user is authorized,
        // if not return any random article
        val article = service.getRandomArticleFromUsersSubscription(userId)
        respondWith(article)
    }

    get("/api/article/get") {
        val article = service.getArticleById(
            articleId = get("id")?.toInt() ?: return@get
        ) ?: return@get
        respondWith(article)
    }

    get("/api/article/all") {
        val topic = get("topic") ?: ""
        val query = get("query") ?: ""

        val articles = service.getAllArticles(
            topic = topic,
            query = query
        )

        val articlesResponse = ArticlesResponse(
            articles = articles
        )
        respondWith(articlesResponse)
    }

//    authenticate {
//        post("/api/article/create") {
//            val response = call.receiveOrNull<CreateArticleRequest>() ?: kotlin.run {
//                respondWithBadRequest()
//                return@post
//            }
//
//            respondWithResult {
//                service.validateAndCreateArticle(response)
//            }
//        }
//    }

    authenticate {
        delete("/api/article/delete") {
            val result = service.deleteArticleById(
                articleId = get("id")?.toInt() ?: return@delete
            )
            when (result) {
                true -> respondWith<Unit>(SlimeResponse(true, "Article deleted successfully"))
                false -> respondWith<Unit>(
                    SlimeResponse(
                        false,
                        "Couldn't complete your request, Please try again later"
                    )
                )
            }
        }
    }

    get("/api/article/explore") {
        val userId = getUserId()
        val articlesInExplore = service.getArticlesInExplore(userId)
        if (articlesInExplore.isNotEmpty()) respondWith(articlesInExplore) else {
            respondWith(
                SlimeResponse(
                    success = true,
                    additionalMessage = "No Recommendation Left",
                    data = emptyList<Article>()
                )
            )
        }
    }
}
