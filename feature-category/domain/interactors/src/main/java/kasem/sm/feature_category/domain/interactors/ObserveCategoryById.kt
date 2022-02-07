/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.ObserverInteractor
import kasem.sm.feature_category.datasource.cache.CategoryDatabaseService
import kasem.sm.feature_category.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveCategoryById @Inject constructor(
    private val cache: CategoryDatabaseService,
    private val mapper: CategoryMapper
) : ObserverInteractor<String, Category>() {
    override suspend fun execute(params: String): Flow<Category> {
        return cache.getCategoryById(params).map { mapper.map(it) }
    }
}
