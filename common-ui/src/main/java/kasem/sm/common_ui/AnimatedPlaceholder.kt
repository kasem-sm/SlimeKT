/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AnimatedPlaceholder(
    hintList: List<String>,
    textStyle: TextStyle = getFont(SlimeTypography.Medium(14.sp)),
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val listOfPlaceholders = rememberSaveable { mutableStateOf(hintList) }
    val currentPlaceholder = rememberSaveable { mutableStateOf(listOfPlaceholders.value.first()) }

    AnimatedContent(
        targetState = currentPlaceholder.value,
        transitionSpec = { ScrollAnimation.scrollTransitionSpec }
    ) { text ->
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }

    LaunchedEffect(key1 = currentPlaceholder.value) {
        val iterator = listOfPlaceholders.value.listIterator()
        while (iterator.hasNext()) {
            delay(3000)
            currentPlaceholder.value = iterator.next()
        }
        while (iterator.hasPrevious()) {
            delay(3000)
            currentPlaceholder.value = iterator.previous()
        }
    }
}

object ScrollAnimation {
    val scrollTransitionSpec: ContentTransform
        get() = slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tweenSpec
        ) + fadeIn() with slideOutVertically(
            targetOffsetY = { -50 },
            animationSpec = tweenSpec
        ) + fadeOut()

    private val tweenSpec = tween<IntOffset>()
}
