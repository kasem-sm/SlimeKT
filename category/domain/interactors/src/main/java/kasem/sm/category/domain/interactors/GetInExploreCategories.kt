/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.category.domain.interactors

import javax.inject.Inject
import kasem.sm.category.datasource.cache.CategoryDatabaseService
import kasem.sm.category.datasource.network.CategoryApiService
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.core.utils.getOrDefault
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GetInExploreCategories @Inject constructor(
    private val api: CategoryApiService,
    private val cache: CategoryDatabaseService,
    private val slimeDispatchers: SlimeDispatchers,
    private val applicationScope: CoroutineScope,
) {
    fun execute(): Flow<Stage> {
        return slimeDispatchers.default.start {
            val categories = api.getExploreCategories().getOrThrow()
                .data.getOrDefault().map {
                    it.toEntity(isInExplore = true)
                }

            categories.let {
                applicationScope.launch {
                    cache.insert(categories)
                }.join()
            }
        }
    }
}
