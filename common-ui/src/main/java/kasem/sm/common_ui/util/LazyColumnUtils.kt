/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

inline fun LazyListScope.dynamicItem(
    predicate: Boolean,
    crossinline content: @Composable () -> Unit,
): LazyListScope = apply {
    if (predicate) {
        item { content() }
    }
}
