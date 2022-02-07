/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Inspired by
 * https://github.com/chrisbanes/tivi/
 */

class ObservableLoader {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val flow: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    val isLoading get() = count.get() > 0

    operator fun invoke(loader: Loader) {
        loadingState.value = when (loader) {
            Loader.START -> count.incrementAndGet()
            Loader.STOP -> count.decrementAndGet()
        }
    }

    operator fun invoke(status: Boolean) {
        loadingState.value = if (status) count.incrementAndGet() else count.decrementAndGet()
    }

    companion object {
        enum class Loader {
            START, STOP
        }
    }
}
