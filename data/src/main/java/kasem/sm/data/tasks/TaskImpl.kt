/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.tasks

import javax.inject.Inject
import kasem.sm.article.worker.DailyReadManager
import kasem.sm.core.domain.Dispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.interfaces.Tasks
import kasem.sm.topic.datasource.cache.dao.TopicDao
import kasem.sm.topic.worker.SubscribeTopicManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TaskImpl @Inject constructor(
    private val dailyReadManager: DailyReadManager,
    private val subscribeTopicManager: SubscribeTopicManager,
    private val dispatchers: Dispatchers,
    private val topicDao: TopicDao
) : Tasks {
    override fun executeDailyReader() = dailyReadManager.execute()

    override suspend fun updateSubscriptionStatus(ids: List<String>): Flow<Stage> {
        return subscribeTopicManager.updateSubscriptionStatus(ids)
    }

    override suspend fun clearUserSubscriptionLocally() {
        withContext(dispatchers.io) {
            topicDao.clearSubscription()
        }
    }
}
