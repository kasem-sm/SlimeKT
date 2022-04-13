/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kasem.sm.common_ui.AnimatedPlaceholder
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeTextField

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchActionClicked: KeyboardActionScope.() -> Unit,
    onTrailingIconClicked: () -> Unit,
) {
    SlimeTextField(
        modifier = modifier,
        input = query,
        onTextChange = onQueryChange,
        shadow = 1.5.dp,
        leadingIconContent = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .padding(bottom = 2.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIconContent = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onTrailingIconClicked) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .size(22.dp)
                            .padding(bottom = 2.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = onSearchActionClicked
        ),
        placeholderContent = {
            AnimatedPlaceholder(
                hints = listOf(
                    stringResource(id = R.string.article_search_txt_1),
                    stringResource(id = R.string.article_search_txt_2),
                    "Search your favourite topic",
                ),
            )
        },
    )
}
