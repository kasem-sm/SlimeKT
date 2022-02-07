/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun SlimeFlowRow(
    mainAxisSpacing: Dp = 10.dp,
    crossAxisSpacing: Dp = 10.dp,
    content: @Composable () -> Unit
) {
    FlowRow(
        mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        mainAxisSize = SizeMode.Expand,
        mainAxisSpacing = mainAxisSpacing,
        crossAxisSpacing = crossAxisSpacing,
        content = content
    )
}
