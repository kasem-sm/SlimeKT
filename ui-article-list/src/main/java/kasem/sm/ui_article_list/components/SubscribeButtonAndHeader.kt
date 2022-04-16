/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.VerticalSpacer
import kasem.sm.common_ui.drawableId
import kasem.sm.common_ui.withScale
import kasem.sm.topic.domain.model.Topic
import kasem.sm.ui_article_list.R

@Composable
internal fun SubscribeButtonAndHeader(
    modifier: Modifier = Modifier,
    topic: Topic,
    onClick: () -> Unit,
    isSubscriptionButtonActive: Boolean,
    isSubscriptionInProgress: Boolean,
) {
    Column(modifier) {
        Text(
            text = topic.title,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 25.withScale(),
            letterSpacing = 1.5.sp,
            fontFamily = LocalSlimeFont.current.semiBold
        )

        VerticalSpacer(value = 10.dp)

        val text = if (isSubscriptionButtonActive) "Unsubscribe" else "Subscribe"
        val icon =
            if (isSubscriptionButtonActive) R.drawable.ic_unsubscribe else R.drawable.ic_subscribe

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SubscribeButton(
                onClick = onClick,
                isActive = isSubscriptionButtonActive,
                text = text,
                trailingIcon = icon,
                isLoading = isSubscriptionInProgress,
                modifier = Modifier.semantics {
                    testTag = "subscribeButton"
                    drawableId = icon
                }
            )

            Text(
                text = "• ${topic.totalSubscribers} Users",
                fontFamily = LocalSlimeFont.current.secondaryMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.withScale()
            )
        }
    }
}
