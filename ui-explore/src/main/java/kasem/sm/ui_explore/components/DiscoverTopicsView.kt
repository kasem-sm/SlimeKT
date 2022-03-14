/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kasem.sm.common_ui.SlimeFlowRow
import kasem.sm.ui_explore.ExploreState

@Composable
internal fun DiscoverTopicsView(
    state: ExploreState,
    onTopicClick: (title: String, id: String) -> Unit
) {
    SlimeFlowRow(
        mainAxisSpacing = 20.dp, crossAxisSpacing = 15.dp
    ) {
        state.topics.forEach { topic ->
            TopicView(topic, onTopicClick)
        }
    }
}
