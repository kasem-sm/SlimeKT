/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.category.domain.interactors

import javax.inject.Inject
import kasem.sm.category.datasource.cache.CategoryDatabaseService
import kasem.sm.category.domain.model.Category
import kasem.sm.core.domain.ObserverInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveInExploreCategories @Inject constructor(
    private val cache: CategoryDatabaseService,
    private val mapper: CategoryMapper
) : ObserverInteractor<Unit, List<Category>>() {
    override suspend fun execute(params: Unit): Flow<List<Category>> {
        return cache.getCategoriesInExplore().map { mapper.map(it) }
    }
}
