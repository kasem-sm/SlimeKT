/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kasem.sm.category.common_ui.CategoryChip
import kasem.sm.category.domain.model.Category
import kasem.sm.common_ui.util.clickWithRipple

@Composable
internal fun CategoryView(
    category: Category,
    onCategoryClick: (title: String, id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CategoryChip(
        category = category,
        modifier = modifier
            .clickWithRipple {
                onCategoryClick(
                    category.title,
                    category.id
                )
            }
    )
}
