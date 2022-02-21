/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.interfaces

import kasem.sm.core.domain.Stage
import kotlinx.coroutines.flow.Flow

/**
 * This interface can help reduce at least 25 seconds on compilation
 * of each module
 */
interface Tasks {
    fun executeDailyReader()
    suspend fun updateSubscriptionStatus(ids: List<String>): Flow<Stage>
}
