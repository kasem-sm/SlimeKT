/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.network.TopicApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopicById @Inject constructor(
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(id: String): Flow<Stage> {
        return dispatchers.default.start {
            val topic = api.getTopicById(id)
                .getOrThrow()
                .data?.toEntity()

            topic?.let {
                cache.insert(it)
            }
        }
    }
}
