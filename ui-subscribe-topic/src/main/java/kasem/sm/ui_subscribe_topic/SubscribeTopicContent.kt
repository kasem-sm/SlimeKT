/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.common_ui.util.dynamicItem
import kasem.sm.ui_subscribe_topic.SubscribeTopicState.Companion.isListEmpty
import kasem.sm.ui_subscribe_topic.components.ContentHeader
import kasem.sm.ui_subscribe_topic.components.EmptyView
import kasem.sm.ui_subscribe_topic.components.ProceedButton
import kasem.sm.ui_subscribe_topic.components.TopicsListView

@Composable
fun SubscribeTopicContent(
    state: SubscribeTopicState,
    onRefresh: () -> Unit,
    saveRecommendedValues: () -> Unit,
    showAuthenticationSheet: () -> Unit,
    navigateBack: () -> Unit,
    updateList: (Int) -> Unit,
) {
    // TODO("Add Glow")
    SlimeSwipeRefresh(
        refreshing = state.isLoading,
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

                dynamicItem(state.isListEmpty) {
                    EmptyView()
                }

                item {
                    TopicsListView(state, updateList)
                }

                item {
                    ProceedButton(
                        state = state,
                        showAuthenticationSheet = showAuthenticationSheet,
                        navigateBack = navigateBack,
                        saveRecommendedValues = saveRecommendedValues
                    )
                }
            }
        }
    }
}
