/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.core.utils.getOrDefault
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.network.TopicApiService
import kotlinx.coroutines.flow.Flow

class GetSubscribedTopics @Inject constructor(
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(): Flow<Stage> {
        return dispatchers.default.start {
            val topics = api.getSubscribedTopics().getOrThrow()
                .data.getOrDefault().map {
                    it.toEntity()
                }

            cache.insert(topics)
        }
    }
}
