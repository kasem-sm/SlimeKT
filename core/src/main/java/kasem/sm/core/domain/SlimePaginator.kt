/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kotlinx.coroutines.flow.Flow
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
            currentStatus(SlimePaginationStatus.EndOfPaginationStatus(false))
        }

        requestForNextPage(page)
            .onStart { currentStatus(SlimePaginationStatus.OnLoadingStatus(true)) }
            .onCompletion { currentStatus(SlimePaginationStatus.OnLoadingStatus(false)) }
            .collectLatest { stage ->
                when (stage) {
                    is PaginationStage.Success -> {
                        val data = stage.data
                        page++
                        currentStatus(SlimePaginationStatus.OnPageLoaded(page, data))
                        if ((data as List<*>).isEmpty()) {
                            currentStatus(SlimePaginationStatus.EndOfPaginationStatus(true))
                        }
                    }
                    is PaginationStage.Exception -> SlimePaginationStatus.OnError(stage.error)
                    is PaginationStage.PaginationOver -> currentStatus(
                        SlimePaginationStatus.EndOfPaginationStatus(
                            true
                        )
                    )
                }
            }
    }
}
