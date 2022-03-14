/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_profile.components

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import kasem.sm.common_ui.EmptyView

@Composable
internal fun WorkInProgressView(context: Context) {
    EmptyView(
        message = "Work In Progress",
        emoji = "👷‍♂️⚒",
        onContributeClick = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://github.com/kasem-sm/SlimeKT/issues".toUri()
                )
            )
        }
    )
}
