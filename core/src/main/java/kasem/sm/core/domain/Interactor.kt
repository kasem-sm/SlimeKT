/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout

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

/**
 * A Top Level Function that returns a flow of [PaginationStage] which is a sealed class containing
 * [PaginationStage.Success], [PaginationStage.Exception] and [PaginationStage.PaginationOver] object.
 *
 * Please refer to our documentation for more [details](https://kasem-sm.github.io/SlimeKT/guide/interactors/#pagination-interactor)
 */
inline fun <T> CoroutineDispatcher.pagingStage(
    crossinline doWork: suspend FlowCollector<PaginationStage<T>>.() -> T,
): Flow<PaginationStage<T>> {
    return flow {
        try {
            withTimeout(TimeUnit.MINUTES.toMillis(5)) {
                val data = doWork()
                emit(PaginationStage.Success(data))
            }
        } catch (e: TimeoutCancellationException) {
            emit(PaginationStage.Exception(e))
        }
    }.catch { throwable ->
        when (throwable) {
            is PaginationOver -> emit(PaginationStage.PaginationOver)
            else -> emit(PaginationStage.Exception(throwable))
        }
    }.flowOn(this)
}
