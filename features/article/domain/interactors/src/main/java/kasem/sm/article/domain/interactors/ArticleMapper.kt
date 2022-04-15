/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.domain.model.Article

@JvmName("toDomainNullable")
fun ArticleEntity?.toDomain(): Article? {
    return if (this != null) {
        Article(
            id = id,
            title = title,
            description = description,
            featuredImage = featuredImage,
            author = author,
            timestamp = timestamp,
            isShownInDailyRead = isShownInDailyRead,
            isActiveInDailyRead = isActiveInDailyRead,
            topic = topic,
            isInExplore = isInExplore,
            isInBookmark = isInBookmark
        )
    } else return null
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        description = description,
        featuredImage = featuredImage,
        author = author,
        timestamp = timestamp,
        isShownInDailyRead = isShownInDailyRead,
        isActiveInDailyRead = isActiveInDailyRead,
        topic = topic,
        isInExplore = isInExplore,
        isInBookmark = isInBookmark
    )
}

fun List<ArticleEntity>.toDomain(): List<Article> {
    return map {
        it.toDomain()
    }
}
