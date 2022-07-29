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
import kasem.sm.article.markdown.components.TypeBlockQuote
import kasem.sm.article.markdown.components.TypeCodeBlock
import kasem.sm.article.markdown.components.TypeEmphasis
import kasem.sm.article.markdown.components.TypeHeading
import kasem.sm.article.markdown.components.TypeInlineCodeBlock
import kasem.sm.article.markdown.components.TypeLink
import kasem.sm.article.markdown.components.TypeParagraph
import kasem.sm.article.markdown.components.TypeThematicBreak
import kasem.sm.article.markdown.utils.TAG_IMAGE_URL
import org.commonmark.node.BlockQuote
import org.commonmark.node.Code
import org.commonmark.node.Emphasis
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Heading
import org.commonmark.node.Image
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.node.ThematicBreak

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
