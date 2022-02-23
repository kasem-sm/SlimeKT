/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.category.domain.model

data class Category(
    val id: String,
    val title: String,
    val timestamp: Long,
    val isSelected: Boolean = false,
    val totalSubscribers: Int,
    val hasUserSubscribed: Boolean,
) {
    companion object {
        val dummy
            get() = Category(
                id = "-1",
                title = "dummy_category",
                timestamp = 1L,
                totalSubscribers = 0,
                hasUserSubscribed = false,
            )
    }
}
