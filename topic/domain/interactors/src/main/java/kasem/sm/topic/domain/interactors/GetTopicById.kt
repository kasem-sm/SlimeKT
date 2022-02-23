/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.network.TopicApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GetTopicById @Inject constructor(
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
    private val slimeDispatchers: SlimeDispatchers,
    private val applicationScope: CoroutineScope,
) {
    fun execute(id: String): Flow<Stage> {
        return slimeDispatchers.default.start {
            val topic = api.getTopicById(id).getOrThrow()
                .data?.toEntity(cache.isInExplore(id))

            topic?.let {
                applicationScope.launch {
                    cache.insert(it)
                }.join()
            }
        }
    }
}
