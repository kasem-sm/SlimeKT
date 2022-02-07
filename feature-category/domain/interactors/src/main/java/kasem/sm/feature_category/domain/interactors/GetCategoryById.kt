/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.feature_category.datasource.cache.CategoryDatabaseService
import kasem.sm.feature_category.datasource.network.CategoryApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GetCategoryById @Inject constructor(
    private val api: CategoryApiService,
    private val cache: CategoryDatabaseService,
    private val slimeDispatchers: SlimeDispatchers,
    private val applicationScope: CoroutineScope,
) {
    fun execute(id: String): Flow<Stage> {
        return slimeDispatchers.defaultDispatcher.start {
            val category = api.getCategoryById(id).getOrThrow()
                .data?.toEntity(cache.isInExplore(id))

            category?.let {
                applicationScope.launch {
                    cache.insert(it)
                }.join()
            }
        }
    }
}
