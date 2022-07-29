/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.datasource_impl.cache

import kasem.sm.core.utils.slimeSuspendTry
import kasem.sm.core.utils.slimeTry
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kasem.sm.topic.datasource_impl.cache.dao.TopicDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TopicDatabaseServiceImpl @Inject constructor(
    private val dao: TopicDao,
) : TopicDatabaseService {

    override suspend fun insert(topics: List<TopicEntity>) {
        slimeSuspendTry {
            dao.insert(topics)
        }
    }

    override suspend fun insert(topic: TopicEntity) {
        slimeSuspendTry {
            dao.insert(topic)
        }
    }

    override fun getAllTopics(): Flow<List<TopicEntity>> {
        return slimeTry {
            dao.getAllTopics()
        }
    }

    override suspend fun getAllTopicsNonFlow(): List<TopicEntity> {
        return slimeSuspendTry {
            dao.getAllTopicsNonFlow()
        }
    }

    override fun getSubscribedTopics(): Flow<List<TopicEntity>> {
        return slimeTry {
            dao.getSubscribedTopics()
        }
    }

    override fun getTopicsInExplore(): Flow<List<TopicEntity>> {
        return slimeTry {
            dao.getTopicsInExplore()
        }
    }

    override suspend fun isInExplore(id: String): Boolean {
        return dao.getTopicsInExploreNonFlow().any { inExplore ->
            inExplore.id == id
        }
    }

    override fun getTopicById(id: String): Flow<TopicEntity?> {
        return slimeTry {
            dao.getTopicById(id)
        }
    }

    override suspend fun updateSubscriptionStatus(status: Boolean, id: String?) {
        return slimeSuspendTry {
            if (id != null) {
                dao.updateSubscriptionStatus(
                    inSubscription = status,
                    // If the topic is subscribed,
                    // it should be removed from the explore tab too
                    // and vice versa
                    inExplore = !status,
                    id = id
                )
            } else dao.removeAllTopicsFromSubscription()
        }
    }
}
