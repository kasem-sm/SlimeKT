/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

class PaginationOver(override val message: String? = null) : Exception(message)

sealed class PaginationStage<out T> {
    data class Success<out T>(val data: T,) : PaginationStage<T>()
    data class Exception(val error: Throwable) : PaginationStage<Nothing>()
    object PaginationOver : PaginationStage<Nothing>()
}

sealed class SlimePaginationStatus<out T> {
    /**
     * Once requestForNextPage gets succeeded, this function is triggered.
     * [page] the page which was successfully loaded from the server and cached.
     * in here, you may request the data of the page given from the cache
     * and store the new page value into savedState.
     */
    data class OnPageLoaded<out T>(val page: Int, val data: T) : SlimePaginationStatus<T>()
    data class OnException(val throwable: Throwable) : SlimePaginationStatus<Nothing>()
    data class OnLoadingStatus(val value: Boolean) : SlimePaginationStatus<Nothing>()
    data class EndOfPaginationStatus(val value: Boolean) : SlimePaginationStatus<Nothing>()
}
