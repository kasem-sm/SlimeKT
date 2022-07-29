/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.markdown.handler

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import kasem.sm.article.markdown.components.*
import kasem.sm.article.markdown.utils.TAG_IMAGE_URL
import org.commonmark.node.*

data class MarkdownStyle(
    val blockQuoteSpanStyle: SpanStyle = SpanStyle(),
    val paragraphSpanStyle: SpanStyle = SpanStyle(),
    val codeFontStyle: TextStyle = TextStyle(),
    val codeBlockBackgroundColor: Color,
    val codeTextColor: Color,
    val blockQuoteTextColor: Color,
    val blockQuoteBlockColor: Color,
    val h1: TextStyle = TextStyle(),
    val h2: TextStyle = TextStyle(),
    val h3: TextStyle = TextStyle(),
    val h4: TextStyle = TextStyle(),
    val h5: TextStyle = TextStyle(),
    val h6: TextStyle = TextStyle(),
)

@Composable
internal fun SlimeMarkdownHandler(
    spanStyle: MarkdownStyle,
    parent: Node,
    modifier: Modifier = Modifier
) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is BlockQuote -> TypeBlockQuote(spanStyle, child, modifier)
            is Heading -> TypeHeading(spanStyle, child, modifier)
            is Paragraph -> TypeParagraph(child, modifier, spanStyle)
            is FencedCodeBlock -> TypeCodeBlock(child, modifier, spanStyle)
            is ThematicBreak -> TypeThematicBreak(modifier)
        }
        child = child.next
    }
}

@Composable
internal fun AnnotatedString.Builder.AppendMarkdownChildren(
    parent: Node,
    colors: ColorScheme,
    markdownStyle: MarkdownStyle,
) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is Paragraph -> AppendMarkdownChildren(child, colors, markdownStyle)
            is Text -> append(child.literal)
            is Image -> appendInlineContent(TAG_IMAGE_URL, child.destination)
            is Code -> TypeInlineCodeBlock(child, markdownStyle = markdownStyle)
            is Link -> TypeLink(child = child, colors = colors, markdownStyle)
            is HardLineBreak -> append("\n")
            is Emphasis -> TypeEmphasis(child = child, colors = colors, markdownStyle)
            is StrongEmphasis -> TypeEmphasis(child = child, colors = colors, markdownStyle)
        }
        child = child.next
    }
}
