/*
 * Copyright (C) 2021, Kasem S.M
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

class GetInExploreTopics @Inject constructor(
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
    private val slimeDispatchers: SlimeDispatchers,
    private val applicationScope: CoroutineScope,
) {
    fun execute(): Flow<Stage> {
        return slimeDispatchers.default.start {
            val topics = api.getExploreTopics().getOrThrow()
                .data.getOrDefault().map {
                    it.toEntity(isInExplore = true)
                }

            applicationScope.launch {
                cache.insert(topics)
            }.join()
        }
    }
}
