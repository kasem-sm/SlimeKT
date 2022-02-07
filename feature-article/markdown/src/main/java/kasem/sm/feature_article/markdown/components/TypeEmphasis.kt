/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.markdown.components

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kasem.sm.feature_article.markdown.handler.AppendMarkdownChildren
import kasem.sm.feature_article.markdown.handler.MarkdownStyle
import org.commonmark.node.Emphasis
import org.commonmark.node.Node
import org.commonmark.node.StrongEmphasis

@Composable
internal fun AnnotatedString.Builder.TypeEmphasis(
    child: Node,
    colors: ColorScheme,
    markdownStyle: MarkdownStyle,
) {
    val spanStyle = when (child) {
        is Emphasis -> SpanStyle(fontStyle = FontStyle.Italic)
        is StrongEmphasis -> SpanStyle(fontWeight = FontWeight.Bold)
        else -> return
    }
    pushStyle(spanStyle)
    AppendMarkdownChildren(child, colors, markdownStyle)
    pop()
}
