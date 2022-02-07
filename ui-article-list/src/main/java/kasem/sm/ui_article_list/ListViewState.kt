/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import kasem.sm.feature_article.domain.model.Article
import kasem.sm.feature_category.domain.model.Category

data class ListViewState(
    val isLoading: Boolean = true,
    val isSubscriptionInProgress: Boolean = false,
    val currentPage: Int = 0,
    val endOfPagination: Boolean = false,
    val articles: List<Article> = emptyList(),
    val category: Category? = null
) {
    companion object {
        val EMPTY = ListViewState()
        const val DEFAULT_CATEGORY_QUERY = ""
    }
}
