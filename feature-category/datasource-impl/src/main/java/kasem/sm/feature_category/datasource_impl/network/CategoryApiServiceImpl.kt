/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.datasource_impl.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kasem.sm.core.interfaces.Session
import kasem.sm.core.utils.withResult
import kasem.sm.feature_category.datasource.network.CategoryApiService
import kasem.sm.feature_category.datasource.network.response.CategoryResponse
import kasem.sm.feature_category.datasource.network.response.SlimeResponse

internal class CategoryApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val session: Session
) : CategoryApiService {
    override suspend fun getAllCategories(): Result<SlimeResponse<List<CategoryResponse>>> {
        return withResult {
            client.get(GET_ALL_CATEGORIES_ROUTE)
        }
    }

    override suspend fun getCategoryById(id: String): Result<SlimeResponse<CategoryResponse>> {
        return withResult {
            client.get(GET_CATEGORY_BY_ID) {
                parameter("id", id)
            }
        }
    }

    override suspend fun getSubscribedCategories(): Result<SlimeResponse<List<CategoryResponse>>> {
        return withResult {
            client.get(GET_SUBSCRIBED_CATEGORIES_ROUTE) {
                parameter("userId", session.getUserId())
            }
        }
    }

    override suspend fun subscribeToCategory(id: String): Result<SlimeResponse<Unit>> {
        return withResult {
            client.post(SUBSCRIBE_TO_CATEGORY_ROUTE) {
                contentType(ContentType.Application.Json)
                parameter("id", id)
            }
        }
    }

    override suspend fun unsubscribeToCategory(id: String): Result<SlimeResponse<Unit>> {
        return withResult {
            client.post(UN_SUBSCRIBE_TO_CATEGORY_ROUTE) {
                contentType(ContentType.Application.Json)
                parameter("id", id)
            }
        }
    }

    override suspend fun getExploreCategories(): Result<SlimeResponse<List<CategoryResponse>>> {
        return withResult {
            client.get(GET_EXPLORE_CATEGORIES_ROUTE) {
                parameter("userId", session.getUserId())
            }
        }
    }

    override suspend fun hasUserSubscribed(categoryId: String): Result<SlimeResponse<Boolean>> {
        return withResult {
            client.get(VERIFY_IF_IS_SUBSCRIBED_ROUTE) {
                parameter("id", categoryId)
            }
        }
    }

    companion object EndPoints {
        const val GET_ALL_CATEGORIES_ROUTE = "/api/category/all"
        const val GET_CATEGORY_BY_ID = "/api/category/get"
        const val GET_SUBSCRIBED_CATEGORIES_ROUTE = "/api/subscribedCategories/all"
        const val SUBSCRIBE_TO_CATEGORY_ROUTE = "/api/subscribedCategories/subscribe"
        const val UN_SUBSCRIBE_TO_CATEGORY_ROUTE = "/api/subscribedCategories/unsubscribe"
        const val VERIFY_IF_IS_SUBSCRIBED_ROUTE = "/api/subscribedCategories/verify"
        const val GET_EXPLORE_CATEGORIES_ROUTE = "/api/subscribedCategories/explore"
    }
}
