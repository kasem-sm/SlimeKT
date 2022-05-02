/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.slime.ui_home.HomeState
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.SlimePrimaryButton
import kasem.sm.common_ui.TopicChip
import kasem.sm.common_ui.util.toggleWithRipple
import kasem.sm.common_ui.withScale
import kasem.sm.topic.domain.model.Topic

internal fun LazyListScope.TopicsView(
    isLoading: Boolean,
    topics: List<Topic>,
    currentQuery: String,
    onTopicChange: (String) -> Unit,
    navigateToSubscriptionScreen: () -> Unit
) {
    if (!isLoading && topics.isEmpty()) {
        item {
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
                        fontSize = 14.withScale(),
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
        }
    } else {
        item {
            LazyRow {
                items(topics) { topic ->
                    val isSelected = currentQuery == topic.title

                    val backgroundColor = animateColorAsState(
                        targetValue = when (isSelected) {
                            true -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.primaryContainer
                        },
                        animationSpec = tween(500),
                    )

                    val textColor = animateColorAsState(
                        targetValue = when (isSelected) {
                            true -> MaterialTheme.colorScheme.onPrimary
                            false -> MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )

                    TopicChip(
                        topic = topic.title,
                        chipBackgroundColor = backgroundColor.value,
                        chipTextColor = textColor.value,
                        modifier = Modifier
                            .padding(10.dp)
                            .toggleWithRipple(
                                value = currentQuery == topic.title,
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
}
