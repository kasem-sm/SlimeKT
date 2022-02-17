/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import androidx.compose.runtime.Immutable
import kasem.sm.feature_article.domain.model.Article

@Immutable
data class DetailState(
    val isLoading: Boolean = true,
    val article: Article? = null,
) {
    companion object {
        val EMPTY = DetailState()
    }
}
