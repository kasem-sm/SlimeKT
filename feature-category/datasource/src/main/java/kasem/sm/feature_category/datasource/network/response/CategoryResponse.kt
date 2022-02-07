/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_category.datasource.network.response

import kasem.sm.feature_category.datasource.cache.entity.CategoryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val title: String,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("totalSubscribers")
    val totalSubscribers: Int,
    @SerialName("hasUserSubscribed")
    val hasUserSubscribed: Boolean
) {
    fun toEntity(isInExplore: Boolean = false): CategoryEntity {
        return CategoryEntity(
            id = id,
            title = title,
            timestamp = timestamp,
            isInSubscription = hasUserSubscribed,
            isInExplore = isInExplore,
            totalSubscribers = totalSubscribers,
        )
    }
}
