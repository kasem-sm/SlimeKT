/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.ObserverInteractor
import kasem.sm.feature_category.datasource.cache.CategoryDatabaseService
import kasem.sm.feature_category.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveAllCategories @Inject constructor(
    private val cache: CategoryDatabaseService,
    private val mapper: CategoryMapper
) : ObserverInteractor<Unit, List<Category>>() {
    override suspend fun execute(params: Unit): Flow<List<Category>> {
        return cache.getAllCategories().map { mapper.map(it) }
    }
}
