/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
internal fun BottomSheetHandle() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier
                .width(50.dp)
                .height(6.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(100.dp)),
        )
    }
}
