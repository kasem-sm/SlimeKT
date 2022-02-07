/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package com.slime.ui_home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import kasem.sm.common_ui.AnimatedPlaceholder
import kasem.sm.common_ui.SlimeTextField

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchActionClicked: () -> Unit,
    onTrailingIconClicked: () -> Unit,
    placeholders: List<String>
) {
    SlimeTextField(
        modifier = modifier,
        input = query,
        onTextChange = onQueryChange,
        leadingIcon = Icons.Default.Search,
        trailingIcon = if (query.isNotEmpty()) Icons.Default.Clear else null,
        onTrailingIconClicked = onTrailingIconClicked,
        imeAction = ImeAction.Search,
        onSearch = onSearchActionClicked,
        placeholderContent = { AnimatedPlaceholder(hintList = placeholders) }
    )
}
