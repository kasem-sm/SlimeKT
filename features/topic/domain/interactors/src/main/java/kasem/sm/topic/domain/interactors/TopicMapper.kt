/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.topic.domain.interactors

import kasem.sm.topic.datasource.cache.entity.TopicEntity
import kasem.sm.topic.domain.model.Topic

@JvmName("toDomainNullable")
fun TopicEntity?.toDomain(): Topic? {
    return if (this != null) {
        Topic(
            id = id,
            title = title,
            timestamp = timestamp,
            totalSubscribers = totalSubscribers,
            hasUserSubscribed = isInSubscription
        )
    } else null
}

@JvmName("toDomain")
fun TopicEntity.toDomain(): Topic {
    return Topic(
        id = id,
        title = title,
        timestamp = timestamp,
        totalSubscribers = totalSubscribers,
        hasUserSubscribed = isInSubscription
    )
}

fun List<TopicEntity>.toDomain(): List<Topic> {
    return map {
        it.toDomain()
    }
}
