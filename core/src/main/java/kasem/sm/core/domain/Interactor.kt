/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

/**
 * Inspired by
 * https://github.com/chrisbanes/tivi/
 */

/**
 * This function is used in every use-case except for Paging related use-cases.
 * It returns a flow of type [Stage] which is a sealed class containing [Stage.Success] and
 * [Stage.Exception] object. [doWork] is where we perform fetching and caching of the data.
 *
 * Please refer to our documentation for more [details](https://kasem-sm.github.io/SlimeKT/guide/interactors/#default-interactor)
 */
inline fun <T> CoroutineDispatcher.start(
    crossinline doWork: suspend () -> T,
): Flow<Stage> {
    return flow {
        try {
            withTimeout(TimeUnit.MINUTES.toMillis(5)) {
                emit(Stage.Initial)
                doWork.invoke()
                emit(Stage.Success)
            }
        } catch (e: TimeoutCancellationException) {
            emit(Stage.Exception(e))
        }
    }.catch { throwable ->
        emit(Stage.Exception(throwable))
    }.flowOn(this)
}
