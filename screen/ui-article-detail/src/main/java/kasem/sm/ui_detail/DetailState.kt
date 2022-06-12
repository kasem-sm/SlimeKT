/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.compose.runtime.Immutable
import kasem.sm.article.domain.model.Article
import kasem.sm.topic.domain.model.Topic

@Immutable
data class DetailState(
    val isLoading: Boolean = true,
    val article: Article? = null,
    val topic: Topic? = null
) {
    companion object {
        val EMPTY = DetailState()
    }
}
