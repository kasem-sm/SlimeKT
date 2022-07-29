/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import kasem.sm.article.domain.model.Article
import kasem.sm.common_ui.LocalSlimeFont
import kasem.sm.common_ui.SlimeCard
import kasem.sm.common_ui.util.clickWithRipple
import kasem.sm.common_ui.withScale

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    imageLoader: ImageLoader,
    onArticleClick: (Int) -> Unit,
    onBookmarkClick: (Int) -> Unit
) {
    SlimeCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickWithRipple {
                onArticleClick(article.id)
            }
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize(),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp)),
            ) {
                imageLoader.Image(
                    modifier = Modifier
                        .wrapContentSize(),
                    data = article.featuredImage,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = article.title,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = LocalSlimeFont.current.semiBold,
                    fontSize = 14.withScale(),
                    lineHeight = 26.sp,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = withAuthorAndPostedTime(
                            article.author,
                            article.timestamp.toDate()
                        ),
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 5.dp),
                    )

                    val isBookmarked = article.isInBookmark
                    BookmarkBar(
                        modifier = Modifier
                            .clickWithRipple {
                                onBookmarkClick(article.id)
                            }
                            .semantics { testTag = if (isBookmarked) "article_isBookmarked" else "article_isNotBookmarked" },
                        isBookmarked = isBookmarked
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkBar(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean
) {
    Box(
        modifier = modifier
            .size(30.dp)
            .clip(RoundedCornerShape(topStart = 6.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        val icon = when (isBookmarked) {
            true -> Icons.Filled.Bookmark
            false -> Icons.Default.BookmarkBorder
        }
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(15.dp),
            imageVector = icon,
            contentDescription = icon.name,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}
