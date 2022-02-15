/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.datasource.network

import kasem.sm.feature_category.datasource.network.response.CategoryResponse
import kasem.sm.feature_category.datasource.network.response.SlimeResponse

interface CategoryApiService {

    suspend fun getAllCategories(): Result<SlimeResponse<List<CategoryResponse>>>

    suspend fun getCategoryById(id: String): Result<SlimeResponse<CategoryResponse>>

    suspend fun getSubscribedCategories(): Result<SlimeResponse<List<CategoryResponse>>>

    suspend fun subscribeIfNot(id: String): Result<SlimeResponse<String>>

    suspend fun getExploreCategories(): Result<SlimeResponse<List<CategoryResponse>>>
}
