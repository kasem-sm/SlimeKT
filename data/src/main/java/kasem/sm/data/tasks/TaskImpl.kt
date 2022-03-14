/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.tasks

import javax.inject.Inject
import kasem.sm.article.worker.DailyReadManager
import kasem.sm.authentication.worker.CheckAuthenticationManager
import kasem.sm.core.domain.Stage
import kasem.sm.core.interfaces.Tasks
import kasem.sm.topic.worker.SubscribeTopicManager
import kotlinx.coroutines.flow.Flow

class TaskImpl @Inject constructor(
    private val dailyReadManager: DailyReadManager,
    private val subscribeTopicManager: SubscribeTopicManager,
    private val authenticationManager: CheckAuthenticationManager,
    private val clearSessionManager: ClearSessionManager
) : Tasks {
    override fun executeDailyReader() = dailyReadManager.execute()

    override fun executeAuthenticationVerifier() = authenticationManager.execute()

    override fun clearUserSubscriptionLocally() = clearSessionManager.execute()

    override fun updateSubscriptionStatus(ids: List<String>): Flow<Stage> {
        return subscribeTopicManager.updateSubscriptionStatus(ids)
    }
}
