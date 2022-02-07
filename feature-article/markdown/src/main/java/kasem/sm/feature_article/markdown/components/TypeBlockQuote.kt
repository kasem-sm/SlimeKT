/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.markdown.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kasem.sm.feature_article.markdown.handler.AppendMarkdownChildren
import kasem.sm.feature_article.markdown.handler.MarkdownStyle
import org.commonmark.node.BlockQuote

@Composable
internal fun TypeBlockQuote(
    markdownStyle: MarkdownStyle,
    blockQuote: BlockQuote,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.drawBehind {
            drawLine(
                color = markdownStyle.blockQuoteBlockColor,
                strokeWidth = 5f,
                start = Offset(12.dp.value, 0f),
                end = Offset(12.dp.value, size.height)
            )
        }
    ) {
        val text = buildAnnotatedString {
            pushStyle(markdownStyle.blockQuoteSpanStyle)
            AppendMarkdownChildren(blockQuote, MaterialTheme.colorScheme, markdownStyle)
            pop()
        }
        Text(
            text, modifier.padding(start = 20.dp),
            color = markdownStyle.blockQuoteTextColor
        )
    }
}
