/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import javax.inject.Inject
import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kotlinx.coroutines.flow.Flow

class BookmarkArticle @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(articleId: Int): Flow<Stage> {
        return dispatchers.default.start {
            val isArticleBookmarked = cache.getArticleData(articleId).fourth.isBookmarked
            cache.updateBookmarkStatus(!isArticleBookmarked, articleId)
        }
    }
}
