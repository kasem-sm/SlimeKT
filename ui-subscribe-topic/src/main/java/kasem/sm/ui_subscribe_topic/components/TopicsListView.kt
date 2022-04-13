/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic.components

import androidx.compose.runtime.Composable
import kasem.sm.common_ui.SlimeFlowRow
import kasem.sm.ui_subscribe_topic.SubscribeTopicState

@Composable
internal fun TopicsListView(
    state: SubscribeTopicState,
    updateList: (Int) -> Unit
) {
    SlimeFlowRow {
        state.topics.forEachIndexed { itemsIndex, topic ->
            SelectableTopicCard(
                topic = topic,
                enabled = !state.isLoading,
                onClick = {
                    updateList(itemsIndex)
                }
            )
        }
    }
}
