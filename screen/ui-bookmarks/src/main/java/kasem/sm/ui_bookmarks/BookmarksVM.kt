/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kasem.sm.article.domain.interactors.BookmarkArticle
import kasem.sm.article.domain.interactors.ResetBookmarks
import kasem.sm.article.domain.observers.ObserveBookmarkedArticles
import kasem.sm.ui_core.stateIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksVM @Inject constructor(
    private val bookmarkArticle: BookmarkArticle,
    private val resetBookmarks: ResetBookmarks,
    observeBookmarks: ObserveBookmarkedArticles,
) : ViewModel() {

    val bookmarks = observeBookmarks.flow
        .stateIn(
            coroutineScope = viewModelScope,
            initialValue = listOf()
        )

    init {
        observeBookmarks.join(viewModelScope)
    }

    fun resetAllBookmarks() {
        viewModelScope.launch {
            resetBookmarks.execute().collect()
        }
    }

    fun updateBookmarkStatus(articleId: Int) {
        viewModelScope.launch {
            bookmarkArticle.execute(articleId).collect()
        }
    }
}
