/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.tasks

import javax.inject.Inject
import kasem.sm.article.worker.DailyReadManager
import kasem.sm.core.domain.Stage
import kasem.sm.core.interfaces.Tasks
import kasem.sm.topic.worker.SubscribeTopicManager
import kotlinx.coroutines.flow.Flow

class TaskImpl @Inject constructor(
    private val dailyReadManager: DailyReadManager,
    private val subscribeTopicManager: SubscribeTopicManager,
) : Tasks {
    override fun executeDailyReader() = dailyReadManager.execute()

    override suspend fun updateSubscriptionStatus(ids: List<String>): Flow<Stage> {
        return subscribeTopicManager.updateSubscriptionStatus(ids)
    }
}
