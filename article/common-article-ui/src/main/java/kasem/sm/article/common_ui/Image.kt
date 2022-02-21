/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.common_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

// TODO: Modifier for Image ?

@Composable
fun ImageLoader.Image(
    modifier: Modifier = Modifier,
    data: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val painter = rememberAsyncImagePainter(
        model = data,
        imageLoader = this
    )

    Box(modifier = modifier) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                SlimeProgressIndicator(strokeWidth = 2.dp)
            }
            else -> Unit
        }

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
