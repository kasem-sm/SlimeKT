/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.domain.model

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
    val category: String,
)
