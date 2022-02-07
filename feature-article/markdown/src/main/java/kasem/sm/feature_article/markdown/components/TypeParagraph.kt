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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kasem.sm.feature_article.markdown.handler.AppendMarkdownChildren
import kasem.sm.feature_article.markdown.handler.MarkdownStyle
import org.commonmark.node.Image
import org.commonmark.node.Paragraph

@Composable
internal fun TypeParagraph(
    paragraph: Paragraph,
    modifier: Modifier = Modifier,
    markdownStyle: MarkdownStyle,
) {
    if (paragraph.firstChild !is Image || paragraph.firstChild != paragraph.lastChild) {
        Column(modifier = modifier.padding(vertical = 10.dp)) {
            val styledText = buildAnnotatedString {
                pushStyle(markdownStyle.paragraphSpanStyle)
                AppendMarkdownChildren(paragraph, MaterialTheme.colorScheme, markdownStyle)
                pop()
            }
            TypeText(styledText, TextStyle(lineHeight = 35.sp))
        }
    }
}
