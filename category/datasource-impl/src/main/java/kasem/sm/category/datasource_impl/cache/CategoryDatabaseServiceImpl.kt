/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.category.datasource_impl.cache

import javax.inject.Inject
import kasem.sm.category.datasource.cache.CategoryDatabaseService
import kasem.sm.category.datasource.cache.dao.CategoryDao
import kasem.sm.category.datasource.cache.entity.CategoryEntity
import kasem.sm.core.utils.slimeSuspendTry
import kasem.sm.core.utils.slimeTry
import kotlinx.coroutines.flow.Flow

internal class CategoryDatabaseServiceImpl @Inject constructor(
    private val dao: CategoryDao,
) : CategoryDatabaseService {

    override suspend fun insert(categories: List<CategoryEntity>) {
        slimeSuspendTry {
            dao.insert(categories)
        }
    }

    override suspend fun insert(category: CategoryEntity) {
        slimeSuspendTry {
            dao.insert(category)
        }
    }

    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return slimeTry {
            dao.getAllCategories()
        }
    }

    override fun getSubscribedCategories(): Flow<List<CategoryEntity>> {
        return slimeTry {
            dao.getSubscribedCategories()
        }
    }

    override fun getCategoriesInExplore(): Flow<List<CategoryEntity>> {
        return slimeTry {
            dao.getCategoriesInExplore()
        }
    }

    override suspend fun isInExplore(id: String): Boolean {
        return dao.getCategoriesInExploreNonFlow().any { inExplore ->
            inExplore.id == id
        }
    }

    override fun getCategoryById(id: String): Flow<CategoryEntity?> {
        return slimeTry {
            dao.getCategoryById(id)
        }
    }

    override suspend fun updateSubscriptionStatus(status: Boolean, id: String) {
        slimeSuspendTry {
            dao.updateSubscriptionStatus(
                inSubscription = status,
                // If the category is subscribed,
                // it should be removed from the explore tab too
                // and vice versa
                inExplore = !status,
                id = id
            )
        }
    }
}
