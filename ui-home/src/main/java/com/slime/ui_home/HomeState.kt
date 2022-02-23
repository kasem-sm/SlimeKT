/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.compose.runtime.Immutable
import kasem.sm.article.domain.model.Article
import kasem.sm.topic.domain.model.Topic

@Immutable
data class HomeState(
    val currentTopic: String = "",
    val currentQuery: String = "",
    val currentPage: Int = 0,
    val isLoading: Boolean = true,
    val topics: List<Topic> = emptyList(),
    val dailyReadArticle: Article? = null,
    val articles: List<Article> = emptyList(),
    val paginationLoadStatus: Boolean = true,
    val endOfPagination: Boolean = false,
) {
    companion object {
        val EMPTY = HomeState()
        const val DEFAULT_TOPIC_QUERY = ""
        const val DEFAULT_SEARCH_QUERY = ""
    }
}
