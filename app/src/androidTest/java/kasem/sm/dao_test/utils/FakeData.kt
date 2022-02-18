/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.dao_test.utils

import kasem.sm.article.datasource.cache.entity.ArticleEntity

object FakeData {
    val sampleEntity: ArticleEntity
        get() = ArticleEntity(
            id = -1, title = "title",
            description = "description",
            category = "category",
            featuredImage = "featured_img",
            author = "author",
            timestamp = 1L, isShownInDailyRead = false, isActiveInDailyRead = true
        )
}
