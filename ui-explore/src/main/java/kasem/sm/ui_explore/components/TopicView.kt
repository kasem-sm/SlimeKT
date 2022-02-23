/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_explore.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.util.clickWithRipple
import kasem.sm.topic.common_ui.TopicChip
import kasem.sm.topic.domain.model.Topic

@Composable
internal fun TopicView(
    topic: Topic,
    onTopicClick: (title: String, id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopicChip(
        topic = topic.title,
        modifier = modifier
            .clickWithRipple {
                onTopicClick(
                    topic.title,
                    topic.id
                )
            }
    )
}
