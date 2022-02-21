/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kasem.sm.category.common_ui.CategoryChip
import kasem.sm.category.domain.model.Category
import kasem.sm.common_ui.util.clickWithRipple

@Composable
internal fun SelectableCategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit
) {
    val backgroundColor = when (category.isSelected) {
        true -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    val textColor = when (category.isSelected) {
        true -> MaterialTheme.colorScheme.onPrimary
        false -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    CategoryChip(
        category = category.title,
        modifier = modifier
            .clickWithRipple { onClick() },
        chipBackgroundColor = backgroundColor,
        chipTextColor = textColor
    )
}
