/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.utils

fun <T> List<T>?.getOrDefault(): List<T> = this ?: listOf()
