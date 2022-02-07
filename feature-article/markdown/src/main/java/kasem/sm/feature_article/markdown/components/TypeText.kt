/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.markdown.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import kasem.sm.feature_article.markdown.utils.TAG_URL

@Composable
internal fun TypeText(
    string: AnnotatedString,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    Text(
        text = string,
        modifier = modifier.pointerInput(true) {
            detectTapGestures { offset ->
                layoutResult.value?.let { layoutResult ->
                    val position = layoutResult.getOffsetForPosition(offset)
                    string.getStringAnnotations(position, position)
                        .firstOrNull()?.let { stringRange ->
                            if (stringRange.tag == TAG_URL) {
                                uriHandler.openUri(stringRange.item)
                            }
                        }
                }
            }
        },
        style = style,
        color = MaterialTheme.colorScheme.onSurface,
        onTextLayout = { layoutResult.value = it }
    )
}
