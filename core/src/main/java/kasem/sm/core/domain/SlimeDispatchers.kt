/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kotlinx.coroutines.CoroutineDispatcher

data class SlimeDispatchers(
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {
    companion object {
        fun createTestDispatchers(coroutineDispatcher: CoroutineDispatcher): SlimeDispatchers {
            return SlimeDispatchers(
                default = coroutineDispatcher,
                main = coroutineDispatcher,
                io = coroutineDispatcher
            )
        }
    }
}
