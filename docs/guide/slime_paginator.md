A custom class that helps us in Pagination by requesting the next page or triggering PaginationOver Exception when necessary. It returns `SlimePaginationStatus` sealed class.

```kotlin
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
```

```kotlin
sealed class SlimePaginationStatus<out T> {
    /**
     * Once requestForNextPage gets succeeded, this function is triggered.
     * [page] the page which was successfully loaded from the server and cached.
     * in here, you may request the data of the page given from the cache
     * and store the new page value into savedState.
     */
    data class OnPageLoaded<out T>(val page: Int, val data: T) : SlimePaginationStatus<T>()
    data class OnError(val error: Throwable) : SlimePaginationStatus<Nothing>()
    data class OnLoadingStatus(val value: Boolean) : SlimePaginationStatus<Nothing>()
    data class EndOfPaginationStatus(val value: Boolean) : SlimePaginationStatus<Nothing>()
}
```