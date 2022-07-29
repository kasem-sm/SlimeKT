/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

import kasem.sm.article.datasource.cache.ArticleDatabaseService
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetBookmarks @Inject constructor(
    private val cache: ArticleDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(): Flow<Stage> {
        return dispatchers.default.start {
            cache.resetAllBookmarks()
        }
    }
}
