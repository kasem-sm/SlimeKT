/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.ObserverInteractor
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveInExploreTopics @Inject constructor(
    private val cache: TopicDatabaseService,
    private val mapper: TopicMapper
) : ObserverInteractor<Unit, List<Topic>>() {
    override suspend fun execute(params: Unit): Flow<List<Topic>> {
        return cache.getTopicsInExplore().map { mapper.map(it) }
    }
}
