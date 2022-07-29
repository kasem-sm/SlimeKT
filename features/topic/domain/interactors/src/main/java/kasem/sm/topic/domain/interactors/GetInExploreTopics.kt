/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import kasem.sm.core.domain.SlimeDispatchers
import kasem.sm.core.domain.Stage
import kasem.sm.core.domain.start
import kasem.sm.core.utils.getOrDefault
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.datasource.network.TopicApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInExploreTopics @Inject constructor(
    private val api: TopicApiService,
    private val cache: TopicDatabaseService,
    private val dispatchers: SlimeDispatchers,
) {
    fun execute(): Flow<Stage> {
        return dispatchers.default.start {
            val apiTopics = api.getExploreTopics()
                .getOrThrow()
                .data.getOrDefault().map {
                    it.toEntity()
                }

            /**
             * 1. Any topics that are not subscribed by the user will be displayed in explore section.
             *
             * 2. Imagine a scenario where the user log's out
             * & therefore all the topics will be pushed into explore section.
             *
             * 3. The user then log's in.
             * 4. Visits the Explore section and tries to fetch his explore topics (unsubscribed topics),
             *
             * 5. our API call will do the work and get the unsubscribed topics.
             *
             * 6. but remember that all topics were moved into Explore section when the user previously signed out,
             * so we will have to filter the actual Explore topics of API from the cache explore topics
             * and move the rest to the subscription section.
             * (as topics that aren't subscribed are in explore section and vice versa)
             */

            cache.getAllTopicsNonFlow()
                .filter { ent ->
                    apiTopics.any { api ->
                        api.id != ent.id
                    }
                }
                .map {
                    cache.updateSubscriptionStatus(true, it.id)
                }

            cache.insert(apiTopics)
        }
    }
}
