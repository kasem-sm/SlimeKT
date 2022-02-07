/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home

import kasem.sm.feature_article.domain.model.Article
import kasem.sm.feature_category.domain.model.Category

data class HomeViewState(
    val currentCategory: String = "",
    val currentQuery: String = "",
    val currentPage: Int = 0,
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val dailyReadArticle: Article? = null,
    val articles: List<Article> = emptyList(),
    val endOfPagination: Boolean = false,
) {
    companion object {
        val EMPTY = HomeViewState()
        const val DEFAULT_CATEGORY_QUERY = ""
        const val DEFAULT_SEARCH_QUERY = ""
    }
}
