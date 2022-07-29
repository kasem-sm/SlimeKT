/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.task_impl

import kasem.sm.article.daily_read_worker.DailyReadManager
import kasem.sm.authentication.auth_verify_worker.CheckAuthenticationManager
import kasem.sm.task_api.Tasks
import kasem.sm.topic.subscription_manager_worker.ClearSubscriptionLocallyManager
import kasem.sm.topic.subscription_manager_worker.SubscribeTopicManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskImpl @Inject constructor(
    private val dailyReadManager: DailyReadManager,
    private val subscribeTopicManager: SubscribeTopicManager,
    private val authenticationManager: CheckAuthenticationManager,
    private val clearSubscriptionLocallyManager: ClearSubscriptionLocallyManager
) : Tasks {
    override fun executeDailyReader() = dailyReadManager.execute()

    override fun executeAuthenticationVerifier() = authenticationManager.execute()

    override fun clearUserSubscriptionLocally() = clearSubscriptionLocallyManager.execute()

    override fun updateSubscriptionStatus(ids: List<String>): Flow<Result<Unit>> {
        return subscribeTopicManager.updateSubscriptionStatus(ids)
    }
}
