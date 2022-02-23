/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.ui_auth.common.AuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DiscoverableToOtherUsersToggle(
    state: AuthState,
    toggleAccountDiscoverability: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = state.isAccountDiscoverable,
            onCheckedChange = toggleAccountDiscoverability
        )
        Text(
            text = stringResource(id = R.string.auth_usr_msg),
            color = MaterialTheme.colorScheme.onBackground,
            letterSpacing = 0.5.sp,
            fontSize = 14.sp,
            fontFamily = LocalSlimeFont.current.medium
        )
    }
}
