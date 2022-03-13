/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.tasks

import javax.inject.Inject
import kasem.sm.article.datasource.cache.dao.ArticleDao
import kasem.sm.article.worker.DailyReadManager
import kasem.sm.authentication.worker.CheckAuthenticationManager
import kasem.sm.core.domain.Stage
import kasem.sm.core.interfaces.Tasks
import kasem.sm.topic.datasource.cache.dao.TopicDao
import kasem.sm.topic.worker.SubscribeTopicManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskImpl @Inject constructor(
    private val dailyReadManager: DailyReadManager,
    private val subscribeTopicManager: SubscribeTopicManager,
    private val authenticationManager: CheckAuthenticationManager,
    private val topicDao: TopicDao,
    private val articleDao: ArticleDao,
    private val applicationScope: CoroutineScope,
) : Tasks {
    override fun executeDailyReader() = dailyReadManager.execute()

    override fun executeAuthenticationVerifier() = authenticationManager.execute()

    override suspend fun updateSubscriptionStatus(ids: List<String>): Flow<Stage> {
        return subscribeTopicManager.updateSubscriptionStatus(ids)
    }

    override suspend fun clearUserSubscriptionLocally() {
        applicationScope.launch {
            topicDao.clearSubscription()
        }.join()
    }

    override suspend fun clearArticlesInExploreLocally() {
        applicationScope.launch {
            articleDao.clearArticlesInExplore()
        }.join()
    }
}
