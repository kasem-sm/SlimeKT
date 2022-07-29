/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

suspend fun <T> ListIterator<T>.doWhenHasNextOrPrevious(
    delayMills: Long = 3000,
    doWork: suspend (T) -> Unit
) {
    while (hasNext() || hasPrevious()) {
        while (hasNext()) {
            delay(delayMills)
            doWork(next())
        }
        while (hasPrevious()) {
            delay(delayMills)
            doWork(previous())
        }
    }
}

@Composable
fun AnimatedPlaceholder(
    hints: List<String>,
    textStyle: FontFamily = LocalSlimeFont.current.medium,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val iterator = hints.listIterator()

    val target by produceState(initialValue = hints.first()) {
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = { ScrollAnimation() }
    ) { str ->
        Text(
            text = str,
            fontFamily = textStyle,
            color = textColor,
            fontSize = 14.withScale(),
        )
    }
}

object ScrollAnimation {
    operator fun invoke(): ContentTransform {
        return slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tween()
        ) + fadeIn() with slideOutVertically(
            targetOffsetY = { -50 },
            animationSpec = tween()
        ) + fadeOut()
    }
}

/**
 * This prevents text size from enlarging when
 * user increases their devices' font size
 */

@Composable
fun Int.withScale(): TextUnit {
    return with(LocalDensity.current) {
        (this@withScale / fontScale).sp
    }
}
