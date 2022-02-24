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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GetSubscribedTopics @Inject constructor(
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
    private val applicationScope: CoroutineScope,
    private val slimeDispatchers: SlimeDispatchers,
) {
    fun execute(): Flow<Stage> {
        return slimeDispatchers.default.start {
            val topics = api.getSubscribedTopics().getOrThrow()
                .data.getOrDefault().map {
                    it.toEntity()
                }

            applicationScope.launch {
                cache.insert(topics)
            }.join()
        }
    }
}
