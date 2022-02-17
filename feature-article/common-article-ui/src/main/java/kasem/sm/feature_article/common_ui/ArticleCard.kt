/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_article.common_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.util.clickWithRipple
import kasem.sm.feature_article.domain.model.Article

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    index: Int = 0,
) {
    SlimeCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(if (index == 0) 0.dp else 5.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickWithRipple {
                onArticleClick(article.id)
            },
    ) {
        Row(
            modifier = Modifier
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = article.title,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = LocalSlimeFont.current.semiBold,
                    fontSize = 14.sp,
                    lineHeight = 26.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SlimeGradientProfile(
                        size = 20.dp,
                        textSize = 12.sp,
                        username = article.author
                    )

                    Text(
                        text = withAuthorAndPostedTime(
                            article.author,
                            article.timestamp.toDate()
                        ),
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                }
            }

            imageLoader.Image(
                modifier = Modifier
                    .weight(0.2f)
                    .height(100.dp)
                    .clip(RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp)),
                data = article.featuredImage,
                contentScale = ContentScale.Crop
            )
        }
    }
}
