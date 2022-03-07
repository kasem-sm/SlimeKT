/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kasem.sm.core.domain.SlimePaginationStatus.EndOfPaginationStatus
import kasem.sm.core.domain.SlimePaginationStatus.OnException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * A custom class that help us in Pagination by requesting next page or
 * triggering [PaginationOver] Exception when necessary.
 * It returns [SlimePaginationStatus] sealed class.
 */
class SlimePaginator<T>(
    /**
     * in here, you may request data from the server and store it into the cache
     * [page] starts from 0 and is incremented after every success,
     * until PaginationOver is emitted
     */
    private val requestForNextPage: (page: Int) -> Flow<PaginationStage<T>>,
    private val currentStatus: suspend (status: SlimePaginationStatus<T>) -> Unit,
    private var page: Int
) {
    /**
     * After process death, request data from [updatedPage] as prior data
     * would be loaded from cache
     */

    suspend fun execute(
        updatedPage: Int? = null,
    ) {
        updatedPage?.let {
            page = it
            currentStatus(EndOfPaginationStatus(false))
        }

        try {
            requestForNextPage(page)
                .onStart { currentStatus(SlimePaginationStatus.OnLoadingStatus(true)) }
                .onCompletion { currentStatus(SlimePaginationStatus.OnLoadingStatus(false)) }
                .catch { currentStatus(OnException(it)) }
                .collectLatest { stage ->
                    currentStatus(
                        when (stage) {
                            is PaginationStage.Success -> {
                                val data = stage.data
                                page++
                                if ((data as List<*>).isEmpty()) {
                                    EndOfPaginationStatus(true)
                                } else SlimePaginationStatus.OnPageLoaded(page, data)
                            }
                            is PaginationStage.Exception -> OnException(stage.error)
                            is PaginationStage.PaginationOver -> EndOfPaginationStatus(true)
                        }
                    )
                }
        } catch (e: Exception) {
            currentStatus(OnException(e))
        }
    }
}
