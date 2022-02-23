/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore

import androidx.compose.runtime.Immutable
import kasem.sm.article.domain.model.Article
import kasem.sm.topic.domain.model.Topic

@Immutable
data class ExploreState(
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
    val topics: List<Topic> = emptyList()
) {

    companion object {
        val EMPTY = ExploreState()
    }
}
