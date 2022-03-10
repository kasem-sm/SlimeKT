/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SlimeTextField(
    modifier: Modifier = Modifier,
    input: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    shadow: Dp = 0.dp,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIconContent: @Composable () -> Unit,
    placeholderContent: @Composable (() -> Unit)? = null,
    trailingIconContent: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .shadow(shadow, shape = shape, clip = true)
            .clip(shape),
        value = input,
        onValueChange = onTextChange,
        enabled = enabled,
        singleLine = true,
        shape = shape,
        textStyle = TextStyle(
            fontFamily = LocalSlimeFont.current.semiBold,
            fontSize = 14.sp,
            letterSpacing = 1.sp
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIconContent,
        trailingIcon = trailingIconContent,
        colors = defaultTextFieldColors(),
        placeholder = placeholderContent,
    )
}

@Composable
fun defaultTextFieldColors() = textFieldColors(
    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
)
