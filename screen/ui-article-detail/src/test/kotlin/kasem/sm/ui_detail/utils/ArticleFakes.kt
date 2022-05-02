/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail.utils

import kasem.sm.article.domain.model.Article

internal object ArticleFakes {
    fun getMockDomain(): Article {
        return Article(
            id = 1,
            title = "title",
            description = "description",
            featuredImage = "img",
            author = "author",
            timestamp = 1L,
            hasUserSeen = true,
            isShownInDailyRead = true,
            isActiveInDailyRead = true,
            isInExplore = true,
            topic = "topic"
        )
    }
}
