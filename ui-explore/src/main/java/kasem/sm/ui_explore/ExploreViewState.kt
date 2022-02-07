/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import kasem.sm.feature_article.domain.model.Article
import kasem.sm.feature_category.domain.model.Category

data class ExploreViewState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val categories: List<Category> = emptyList()
) {

    companion object {
        val EMPTY = ExploreViewState()
    }
}
