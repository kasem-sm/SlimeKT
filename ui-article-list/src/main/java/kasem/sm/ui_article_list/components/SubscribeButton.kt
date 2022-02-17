/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_article_list.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kasem.sm.common_ui.SlimeDoubleRoleButton

@Composable
internal fun SubscribeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isActive: Boolean,
    text: String,
    @DrawableRes trailingIcon: Int,
    isLoading: Boolean
) {
    SlimeDoubleRoleButton(
        modifier = modifier.wrapContentSize(),
        isActive = isActive,
        onClick = onClick,
        text = text,
        trailingIcon = trailingIcon,
        isLoading = isLoading,
        enabled = !isLoading
    )
}
