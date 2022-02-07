/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.markdown.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kasem.sm.feature_article.markdown.handler.MarkdownStyle
import org.commonmark.node.Code
import org.commonmark.node.FencedCodeBlock

@Composable
internal fun TypeCodeBlock(
    fencedCodeBlock: FencedCodeBlock,
    modifier: Modifier = Modifier,
    markdownStyle: MarkdownStyle
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(6.dp),
        color = markdownStyle.codeBlockBackgroundColor,
    ) {
        Text(
            text = fencedCodeBlock.literal,
            style = markdownStyle.codeFontStyle,
            modifier = Modifier
                .padding(10.dp),
            color = markdownStyle.codeTextColor
        )
    }
}

@Composable
internal fun AnnotatedString.Builder.TypeInlineCodeBlock(
    child: Code,
    markdownStyle: MarkdownStyle
) {
    pushStyle(
        TextStyle(fontFamily = markdownStyle.codeFontStyle.fontFamily).toSpanStyle()
            .copy(
                background = markdownStyle.codeBlockBackgroundColor,
                color = markdownStyle.codeTextColor
            )
    )
    append(child.literal)
    pop()
}
