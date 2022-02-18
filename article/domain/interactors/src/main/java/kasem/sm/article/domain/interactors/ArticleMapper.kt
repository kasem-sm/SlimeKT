/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import javax.inject.Inject
import javax.inject.Singleton
import kasem.sm.article.datasource.cache.entity.ArticleEntity
import kasem.sm.article.domain.model.Article
import kasem.sm.core.utils.IMapper

@Singleton
class ArticleMapper @Inject constructor() : IMapper<ArticleEntity, Article> {
    override suspend fun map(from: ArticleEntity?): Article {
        return if (from != null) {
            Article(
                id = from.id,
                title = from.title,
                description = from.description,
                featuredImage = from.featuredImage,
                author = from.author,
                timestamp = from.timestamp,
                isShownInDailyRead = from.isShownInDailyRead,
                isActiveInDailyRead = from.isActiveInDailyRead,
                category = from.category
            )
        } else throw Exception()
    }

    override suspend fun map(from: List<ArticleEntity>): List<Article> {
        return from.map {
            map(it)
        }
    }
}
