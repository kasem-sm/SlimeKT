/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.interfaces

import kasem.sm.core.domain.Stage
import kotlinx.coroutines.flow.Flow

interface Tasks {
    fun executeDailyReader()
    suspend fun updateSubscriptionStatus(ids: List<String>): Flow<Stage>
    suspend fun clearUserSubscriptionLocally()
    suspend fun clearArticlesInExploreLocally()
}
