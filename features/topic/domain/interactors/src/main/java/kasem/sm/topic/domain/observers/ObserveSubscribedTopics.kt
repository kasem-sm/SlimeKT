/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.observers

import javax.inject.Inject
import kasem.sm.core.domain.ObserverInteractor
import kasem.sm.topic.datasource.cache.TopicDatabaseService
import kasem.sm.topic.domain.interactors.toDomain
import kasem.sm.topic.domain.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveSubscribedTopics @Inject constructor(
    private val cache: TopicDatabaseService,
) : ObserverInteractor<Unit, List<Topic>>() {
    override fun execute(params: Unit): Flow<List<Topic>> {
        return cache.getSubscribedTopics().map { it.toDomain() }
    }
}
