/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.datasource.utils

abstract class ArticleData

data class DailyReadStatus(val dailyReadStatus: Boolean) : ArticleData()

data class IsActiveInDailyRead(val isActiveInDailyRead: Boolean) : ArticleData()

data class IsInExplore(val isInExplore: Boolean) : ArticleData()

data class IsBookmarked(val isBookmarked: Boolean) : ArticleData()