/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.TopicChip
import kasem.sm.common_ui.util.clickWithRipple
import kasem.sm.topic.domain.model.Topic

@Composable
internal fun SelectableTopicCard(
    modifier: Modifier = Modifier,
    topic: Topic,
    onClick: () -> Unit
) {
    val backgroundColor = when (topic.isSelected) {
        true -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.primaryContainer
    }

    val textColor = when (topic.isSelected) {
        true -> MaterialTheme.colorScheme.onPrimary
        false -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    TopicChip(
        topic = topic.title,
        modifier = modifier
            .clickWithRipple { onClick() },
        chipBackgroundColor = backgroundColor,
        chipTextColor = textColor
    )
}
