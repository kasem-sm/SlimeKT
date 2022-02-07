/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_detail

import kasem.sm.feature_article.domain.model.Article

data class ArticleDetailState(
    val isLoading: Boolean = true,
    val article: Article? = null,
) {
    companion object {
        val EMPTY = ArticleDetailState()
    }
}
