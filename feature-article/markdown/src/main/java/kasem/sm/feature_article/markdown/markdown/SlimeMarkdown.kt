/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.markdown.markdown

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kasem.sm.feature_article.markdown.handler.MarkdownStyle
import kasem.sm.feature_article.markdown.handler.SlimeMarkdownHandler
import org.commonmark.node.Document
import org.commonmark.parser.Parser

@Composable
internal fun markdownStyle(): MarkdownStyle {
    return MarkdownStyle(
        codeBlockBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        codeTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        blockQuoteTextColor = MaterialTheme.colorScheme.onBackground,
        blockQuoteBlockColor = MaterialTheme.colorScheme.onBackground
    )
}

@Stable
@Composable
fun SlimeMarkdown(markdownStyle: MarkdownStyle = markdownStyle(), text: String) {
    val parser = Parser.builder().build()
    val root = parser.parse(text) as Document
    SlimeMarkdownHandler(markdownStyle, root)
}
