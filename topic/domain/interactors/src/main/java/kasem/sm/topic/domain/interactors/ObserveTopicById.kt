/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import javax.inject.Inject
import kasem.sm.core.domain.ObserverInteractor
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveTopicById @Inject constructor(
    private val cache: TopicDatabaseService,
    private val mapper: TopicMapper
) : ObserverInteractor<String, Topic>() {
    override suspend fun execute(params: String): Flow<Topic> {
        return cache.getTopicById(params).map { mapper.map(it) }
    }
}