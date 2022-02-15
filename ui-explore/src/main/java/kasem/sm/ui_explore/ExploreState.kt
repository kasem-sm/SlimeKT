/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.compose.runtime.Immutable
import kasem.sm.feature_article.domain.model.Article
import kasem.sm.feature_category.domain.model.Category

@Immutable
data class ExploreState(
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
    val categories: List<Category> = emptyList()
) {

    companion object {
        val EMPTY = ExploreState()
    }
}
