/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.category.domain.interactors

import javax.inject.Inject
import javax.inject.Singleton
import kasem.sm.category.datasource.cache.entity.CategoryEntity
import kasem.sm.category.domain.model.Category
import kasem.sm.core.utils.IMapper

@Singleton
class CategoryMapper @Inject constructor() : IMapper<CategoryEntity, Category> {
    override suspend fun map(from: CategoryEntity?): Category {
        return if (from != null) {
            Category(
                id = from.id,
                title = from.title,
                timestamp = from.timestamp,
                totalSubscribers = from.totalSubscribers,
                hasUserSubscribed = from.isInSubscription
            )
        } else throw Exception()
    }

    override suspend fun map(from: List<CategoryEntity>): List<Category> {
        return from.map {
            map(it)
        }
    }
}
