/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.markdown.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kasem.sm.feature_article.markdown.handler.AppendMarkdownChildren
import kasem.sm.feature_article.markdown.handler.MarkdownStyle
import kasem.sm.feature_article.markdown.handler.SlimeMarkdownHandler
import org.commonmark.node.Heading

@Composable
internal fun TypeHeading(
    markdownStyle: MarkdownStyle,
    heading: Heading,
    modifier: Modifier = Modifier,
) {

    val style = when (heading.level) {
        1 -> markdownStyle.h1
        2 -> markdownStyle.h2
        3 -> markdownStyle.h3
        4 -> markdownStyle.h4
        5 -> markdownStyle.h5
        6 -> markdownStyle.h6
        else -> {
            // Invalid header...
            SlimeMarkdownHandler(markdownStyle, heading)
            return
        }
    }

    Column(modifier = modifier.padding(vertical = 10.dp)) {
        val text = buildAnnotatedString {
            AppendMarkdownChildren(heading, MaterialTheme.colorScheme, markdownStyle)
        }
        TypeText(text, style)
    }
}
