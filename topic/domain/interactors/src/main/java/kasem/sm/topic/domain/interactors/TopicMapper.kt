/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import javax.inject.Inject
import javax.inject.Singleton
import kasem.sm.core.utils.IMapper
import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kasem.sm.topic.domain.model.Topic

@Singleton
class TopicMapper @Inject constructor() : IMapper<TopicEntity, Topic> {
    override suspend fun map(from: TopicEntity?): Topic {
        return if (from != null) {
            Topic(
                id = from.id,
                title = from.title,
                timestamp = from.timestamp,
                totalSubscribers = from.totalSubscribers,
                hasUserSubscribed = from.isInSubscription
            )
        } else throw Exception()
    }

    override suspend fun map(from: List<TopicEntity>): List<Topic> {
        return from.map {
            map(it)
        }
    }
}
