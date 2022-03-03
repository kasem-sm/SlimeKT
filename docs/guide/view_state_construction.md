Every ViewModel exposes a `viewState` to the screen which is made by combining various flows.

```kotlin
    val state: StateFlow<ExploreState> = combine(
        loadingStatus.flow,
        observeLatestArticles.flow,
        observeInExploreTopics.flow
    ) { latestArticleLoading, latestArticles, topics ->
        ExploreState(
            isLoading = latestArticleLoading,
            articles = latestArticles,
            topics = topics
        )
    }.stateIn(viewModelScope, ExploreState.EMPTY)
```
