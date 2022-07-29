/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.utils

import io.ktor.client.request.*

fun HttpRequestBuilder.userIdParam(id: String?) {
    parameter("userId", id)
}
