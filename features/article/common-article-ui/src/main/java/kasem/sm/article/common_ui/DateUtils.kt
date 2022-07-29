/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.article.common_ui

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(
    pattern: String = "dd MMMM"
): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}
