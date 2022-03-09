Every interactor (use-case) in the project leads to code duplications so we have created Top Level functions which help us to avoid it. 

## Default Interactor

This function is used in every use-case except for Paging related use-cases. It returns a `flow` of type `Stage` which is a sealed class containing `Success` and `Exception` objects. `doWork` is where we perform fetching and caching of the data.

```kotlin
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

```

The usage may look like:
```kotlin
fun execute(articleId: Int): Flow<Stage> {
    return dispatchers.default.start {
        val articleFromApi = api.getArticleById(articleId)
                .getOrThrow()

        articleFromApi?.data?.let {
            applicationScope.launch {
                val pair = cache.getRespectivePair(it.id)
                cache.insert(it.toEntity(pair))
             }.join()
        }
    }
}
```

It also has a `.collect()` extension function which is used by our ViewModel to avoid code duplication.

```kotlin
/**
 * [collect] is an extension function which takes in
 * a loader which helps in showing the progressBar for each execution
 * and onError lambda gets triggered if any exception is thrown.
 */
suspend fun Flow<Stage>.collect(
    loader: ObservableLoader,
    onError: suspend (String) -> Unit,
    onSuccess: suspend () -> Unit = { },
) {
    loader.start()
    collectLatest { stage ->
        when (stage) {
            is Stage.Success -> onSuccess.invoke()
            is Stage.Exception -> onError.invoke(stage.throwable.toMessage)
        }
        loader.stop()
    }
}

```

## Pagination Interactor

A Top Level Function that returns a `flow` of `PaginationStage` which is a sealed class containing `Success`, `Exception` and `PaginationOver` objects.

```kotlin
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
```

To see its usage, please checkout [GetArticlesTillPage](https://github.com/kasem-sm/SlimeKT/blob/dev/features/article/domain/interactors/src/main/java/kasem/sm/article/domain/interactors/GetArticlesTillPage.kt) interactor.
