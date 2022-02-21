/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.R
import kasem.sm.common_ui.SlimeTextField

@Composable
fun UsernameField(
    modifier: Modifier = Modifier,
    text: String,
    onUsernameChanged: (String) -> Unit,
    onNextClicked: KeyboardActionScope.() -> Unit,
    enabled: Boolean = true,
) {
    SlimeTextField(
        modifier = modifier,
        input = text,
        onTextChange = onUsernameChanged,
        enabled = enabled,
        leadingIconContent = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
                    .padding(bottom = 2.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholderContent = {
            Text(
                text = stringResource(id = R.string.username),
                fontFamily = LocalSlimeFont.current.medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onDone = onNextClicked
        ),
    )
}
