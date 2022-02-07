/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kotlinx.coroutines.CoroutineDispatcher

data class SlimeDispatchers(
    val defaultDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher
)
