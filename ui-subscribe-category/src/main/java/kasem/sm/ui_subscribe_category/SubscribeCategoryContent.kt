/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kasem.sm.common_ui.SlimeFlowRow
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.feature_category.domain.model.Category
import kasem.sm.ui_subscribe_category.components.ContentHeader
import kasem.sm.ui_subscribe_category.components.SelectableCategoryCard

@Composable
fun SubscribeCategoryContent(
    isLoading: Boolean,
    listOfCategories: List<Category>,
    onRefresh: () -> Unit,
    saveRecommendedValues: () -> Unit,
    updateList: (Int) -> Unit
) {
    // TODO("Add Glow")
    // refreshing is explicitly false as we already are
    // showing a progress indicator on our Proceed button.
    SlimeSwipeRefresh(
        refreshing = false,
        onRefresh = onRefresh
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            SlimeScreenColumn(verticalArrangement = Arrangement.SpaceBetween) {
                item {
                    ContentHeader()
                }

                item {
                    SlimeFlowRow {
                        listOfCategories.forEachIndexed { itemsIndex, category ->
                            SelectableCategoryCard(
                                category = category,
                                onClick = {
                                    updateList(itemsIndex)
                                }
                            )
                        }
                    }
                }

                item {
                    SlimePrimaryButton(
                        isLoading = isLoading,
                        text = stringResource(R.string.proceed),
                        onClick = { saveRecommendedValues() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = !isLoading
                    )
                }
            }
        }
    }
}
