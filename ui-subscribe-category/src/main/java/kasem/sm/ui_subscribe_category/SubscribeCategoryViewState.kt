/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import kasem.sm.feature_category.domain.model.Category

data class SubscribeCategoryViewState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
) {
    companion object {
        val EMPTY = SubscribeCategoryViewState()
    }
}
