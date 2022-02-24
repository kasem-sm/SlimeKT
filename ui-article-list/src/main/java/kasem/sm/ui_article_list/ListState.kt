/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list

import androidx.compose.runtime.Immutable
import kasem.sm.article.domain.model.Article
import kasem.sm.topic.domain.model.Topic

@Immutable
data class ListState(
    val isLoading: Boolean = true,
    val isSubscriptionInProgress: Boolean = false,
    val currentPage: Int = 0,
    val endOfPagination: Boolean = false,
    val articles: List<Article> = emptyList(),
    val topic: Topic? = null,
    val isUserAuthenticated: Boolean = false
) {
    companion object {
        val EMPTY = ListState()
    }
}
