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
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.util.toggleWithRipple
import kasem.sm.topic.common_ui.TopicChip
import kasem.sm.topic.domain.model.Topic

@Composable
internal fun TopicsRow(
    isLoading: Boolean,
    topics: List<Topic>,
    currentTopic: String,
    onTopicChange: (String) -> Unit,
    navigateToSubscriptionScreen: () -> Unit
) {
    if (!isLoading && topics.isEmpty()) {
        SlimeCard(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.subscribe_to_topic_header),
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
            items(topics) { topic ->
                val isSelected = currentTopic == topic.title

                val backgroundColor = when (isSelected) {
                    true -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.primaryContainer
                }

                val textColor = when (isSelected) {
                    true -> MaterialTheme.colorScheme.onPrimary
                    false -> MaterialTheme.colorScheme.onPrimaryContainer
                }

                TopicChip(
                    topic = topic.title,
                    chipBackgroundColor = backgroundColor,
                    chipTextColor = textColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .toggleWithRipple(
                            value = currentTopic == topic.title,
                        ) { value ->
                            when (value) {
                                true -> onTopicChange(topic.title)
                                false -> onTopicChange(HomeState.DEFAULT_TOPIC_QUERY)
                            }
                        }
                )
            }
        }
    }
}
