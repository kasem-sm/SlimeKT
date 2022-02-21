/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.common_ui

import kotlin.math.roundToInt

/**
 * We estimate that 265 words take 1 min to read, based on that, we will calculate
 * the reading time.
 * https://help.medium.com/hc/en-us/articles/214991667-Read-time#:%7E:text=At%20the%20top%20of%20each,an%20adjustment%20made%20for%20images
 */
// val readTime =
//    (article.description.length / 265).toFloat().roundToInt()

fun Int.toEstimatedTime() = (this / 265).toFloat().roundToInt()
