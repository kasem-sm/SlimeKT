/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.domain.interactors

data class Param(
    val category: String,
    val query: String,
    val page: Int,
    val pageSize: Int
)
