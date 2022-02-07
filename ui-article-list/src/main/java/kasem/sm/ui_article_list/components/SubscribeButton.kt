/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.SlimeDoubleRoleButton
import kasem.sm.ui_article_list.R

@Composable
internal fun SubscribeButton(
    onClick: () -> Unit,
    isActive: Boolean,
) {
    SlimeDoubleRoleButton(
        modifier = Modifier.wrapContentSize(),
        isActive = isActive,
        onClick = onClick,
        text = if (isActive) "Unsubscribe" else "Subscribe",
        trailingIcon = if (isActive) R.drawable.ic_unsubscribe else R.drawable.ic_subscribe
    )
}
