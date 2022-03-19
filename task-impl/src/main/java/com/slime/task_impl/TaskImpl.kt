/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.task_impl

import com.slime.task_api.Tasks
import javax.inject.Inject
import kasem.sm.article.worker.DailyReadManager
import kasem.sm.authentication.worker.CheckAuthenticationManager
import kasem.sm.topic.worker.ClearSubscriptionLocallyManager
import kasem.sm.topic.worker.SubscribeTopicManager
import kotlinx.coroutines.flow.Flow

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
