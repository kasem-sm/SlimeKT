/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slime.ui_home.HomeState
import kasem.sm.category.common_ui.CategoryChip
import kasem.sm.category.domain.model.Category
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.util.toggleWithRipple

@Composable
internal fun CategoriesRow(
    isLoading: Boolean,
    categories: List<Category>,
    currentCategory: String,
    onCategoryChange: (String) -> Unit,
    navigateToSubscriptionScreen: () -> Unit
) {
    if (!isLoading && categories.isEmpty()) {
        SlimeCard(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.subscribe_to_category_header),
                    fontFamily = LocalSlimeFont.current.secondaryMedium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .wrapContentSize(),
                    textAlign = TextAlign.Center
                )
                SlimePrimaryButton(
                    text = stringResource(id = R.string.continue_btn),
                    onClick = navigateToSubscriptionScreen,
                )
            }
        }
    } else {
        LazyRow {
            items(categories) { category ->
                val isSelected = currentCategory == category.title

                val backgroundColor = when (isSelected) {
                    true -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.primaryContainer
                }

                val textColor = when (isSelected) {
                    true -> MaterialTheme.colorScheme.onPrimary
                    false -> MaterialTheme.colorScheme.onPrimaryContainer
                }

                CategoryChip(
                    category = category.title,
                    chipBackgroundColor = backgroundColor,
                    chipTextColor = textColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .toggleWithRipple(
                            value = currentCategory == category.title,
                        ) { value ->
                            when (value) {
                                true -> onCategoryChange(category.title)
                                false -> onCategoryChange(HomeState.DEFAULT_CATEGORY_QUERY)
                            }
                        }
                )
            }
        }
    }
}