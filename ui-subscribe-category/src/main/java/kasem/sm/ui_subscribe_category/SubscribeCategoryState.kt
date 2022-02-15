/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import androidx.compose.runtime.Immutable
import kasem.sm.feature_category.domain.model.Category

@Immutable
data class SubscribeCategoryState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val isUserAuthenticated: Boolean = false
) {
    companion object {
        val EMPTY = SubscribeCategoryState()
    }
}
