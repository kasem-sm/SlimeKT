/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import androidx.compose.runtime.Immutable
import kasem.sm.article.domain.model.Article
import kasem.sm.category.domain.model.Category

@Immutable
data class HomeState(
    val currentCategory: String = "",
    val currentQuery: String = "",
    val currentPage: Int = 0,
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val dailyReadArticle: Article? = null,
    val articles: List<Article> = emptyList(),
    val paginationLoadStatus: Boolean = true,
    val endOfPagination: Boolean = false,
) {
    companion object {
        val EMPTY = HomeState()
        const val DEFAULT_CATEGORY_QUERY = ""
        const val DEFAULT_SEARCH_QUERY = ""
    }
}
