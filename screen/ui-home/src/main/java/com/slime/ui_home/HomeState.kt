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
    val currentQuery: String = "",
    val isLoading: Boolean = true,
    val isUserAuthenticated: Boolean = false,
    val topics: List<Topic> = emptyList(),
    val dailyReadArticle: Article? = null,
    val articles: List<Article> = emptyList(),
) {
    companion object {
        val EMPTY = HomeState()
        const val DEFAULT_TOPIC_QUERY = ""
        const val DEFAULT_SEARCH_QUERY = ""
    }
}
