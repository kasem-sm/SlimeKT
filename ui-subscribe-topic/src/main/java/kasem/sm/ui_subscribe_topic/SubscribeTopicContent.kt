/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R.drawable
import kasem.sm.common_ui.R.string
import kasem.sm.common_ui.SlimeFlowRow
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.SlimeScreenColumn
import kasem.sm.common_ui.SlimeSwipeRefresh
import kasem.sm.common_ui.util.dynamicItem
import kasem.sm.ui_subscribe_topic.SubscribeTopicState.Companion.isListEmpty
import kasem.sm.ui_subscribe_topic.SubscribeTopicState.Companion.loadingStatus
import kasem.sm.ui_subscribe_topic.components.ContentHeader
import kasem.sm.ui_subscribe_topic.components.SelectableTopicCard

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
                    SlimeFlowRow {
                        state.topics.forEachIndexed { itemsIndex, topic ->
                            SelectableTopicCard(
                                topic = topic,
                                onClick = {
                                    updateList(itemsIndex)
                                }
                            )
                        }
                    }
                }

                item {
                    SlimePrimaryButton(
                        isLoading = state.loadingStatus,
                        text = if (!state.isUserAuthenticated) {
                            "Please Sign In to continue"
                        } else if (state.isListEmpty) "Go Back"
                        else stringResource(string.continue_btn),
                        onClick = {
                            if (!state.isUserAuthenticated) {
                                showAuthenticationSheet()
                            } else if (state.isListEmpty) {
                                navigateBack()
                            } else saveRecommendedValues()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.loadingStatus,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        trailingIcon = drawable.ic_lock elseNull { !state.isUserAuthenticated }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "😎",
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
        Text(
            text = "Damn, Slimer!\nYou have subscribed to every topic.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontFamily = LocalSlimeFont.current.secondaryMedium
        )
    }
}

infix fun <T> T.elseNull(predicate: () -> Boolean): T? {
    return if (predicate()) this else null
}
