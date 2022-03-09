/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.buttonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.common_ui.util.TestTags.BUTTON_PROGRESS_BAR

@Composable
internal fun RowScope.buttonContent(
    isLoading: Boolean,
    text: String,
    progressColor: Color = MaterialTheme.colorScheme.onPrimary,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconColor: Color? = null,
) = apply {
    AnimatedVisibility(visible = isLoading) {
        Row {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .semantics { testTag = BUTTON_PROGRESS_BAR },
                color = progressColor,
                strokeWidth = 2.5.dp
            )

            HorizontalSpacer(value = 10.dp)
        }
    }

    if (!isLoading) {
        trailingIcon?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = text,
                tint = trailingIconColor ?: textColor,
                modifier = Modifier.size(20.dp),
            )

            HorizontalSpacer(value = 10.dp)
        }
    }

    Text(
        text = if (isLoading) "Please wait" else text,
        color = textColor,
        fontSize = 14.sp,
        letterSpacing = 1.sp,
        fontFamily = LocalSlimeFont.current.secondaryMedium
    )
}

/**
 * A customizable re-usable elevated button for Slime.
 * @param modifier Can be used to modify the composable from outside
 * @param text the text of the button
 * @param onClick callback invoked when clicking the button
 * @param enabled the state of the button
 */
@Composable
fun SlimeElevatedButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    text: String,
    onClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    enabled: Boolean = true,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = buttonColors(backgroundColor),
        elevation = buttonElevation(1.dp)
    ) {
        buttonContent(isLoading = isLoading, text = text)
    }
}

/**
 * A customizable re-usable primary button for Slime.
 * @param modifier Can be used to modify the composable from outside
 * @param text the text of the button
 * @param onClick callback invoked when clicking the button
 * @param enabled the state of the button
 */
@Composable
fun SlimePrimaryButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    text: String,
    onClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconColor: Color? = null,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        colors = buttonColors(
            if (isLoading) MaterialTheme.colorScheme.secondary else backgroundColor,
            disabledContainerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
    ) {
        buttonContent(
            isLoading = isLoading,
            text = if (isLoading) "Please Wait" else text,
            textColor = textColor,
            trailingIcon = trailingIcon,
            trailingIconColor = trailingIconColor
        )
    }
}

/**
 * A customizable re-usable primary button for Slime.
 * @param modifier Can be used to modify the composable from outside
 * @param text the text of the button
 * @param onClick callback invoked when clicking the button
 * @param enabled the state of the button
 */
@Composable
fun SlimeDoubleRoleButton(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    backgroundColor: Color = if (isActive) MaterialTheme.colorScheme.secondary
    else MaterialTheme.colorScheme.primary,
    contentColor: Color = if (isActive) MaterialTheme.colorScheme.onSecondary
    else MaterialTheme.colorScheme.onPrimary,
    @DrawableRes trailingIcon: Int? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(2.dp)
    ) {
        buttonContent(
            isLoading = isLoading,
            text = text,
            textColor = contentColor,
            trailingIcon = trailingIcon,
            trailingIconColor = contentColor,
        )
    }
}
