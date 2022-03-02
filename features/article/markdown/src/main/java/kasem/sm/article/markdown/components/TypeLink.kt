/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.markdown.components

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import kasem.sm.article.markdown.handler.AppendMarkdownChildren
import kasem.sm.article.markdown.handler.MarkdownStyle
import kasem.sm.article.markdown.utils.TAG_URL
import org.commonmark.node.Link

@Composable
internal fun AnnotatedString.Builder.TypeLink(
    child: Link,
    colors: ColorScheme,
    markdownStyle: MarkdownStyle,
) {
    val underline = SpanStyle(textDecoration = TextDecoration.Underline)
    pushStyle(underline)
    pushStringAnnotation(TAG_URL, child.destination)
    AppendMarkdownChildren(child, colors, markdownStyle)
    pop()
    pop()
}
