/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.model

data class Article(
    val id: Int,
    val title: String,
    val description: String,
    val featuredImage: String,
    val author: String,
    val timestamp: Long,
    val hasUserSeen: Boolean = false,
    val isShownInDailyRead: Boolean,
    val isActiveInDailyRead: Boolean,
    val topic: String,
)
