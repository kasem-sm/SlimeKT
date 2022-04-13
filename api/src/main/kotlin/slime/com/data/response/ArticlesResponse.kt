/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.data.response

import kotlinx.serialization.Serializable
import slime.com.data.models.Article

@Serializable
data class ArticlesResponse(
    val articles: List<Article>? = null
)
