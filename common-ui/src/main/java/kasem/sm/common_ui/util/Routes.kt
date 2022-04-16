/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import android.net.Uri
import androidx.core.net.toUri

sealed class Routes(
    val route: String,
) {
    object Main : Routes("main_route")

    object LoginScreen : Routes("login_screen")

    object RegisterScreen : Routes("sign_up_screen")

    object HomeScreen : Routes("home_screen")

    data class ExploreScreen(val query: String = "{slime_topic}") : Routes(
        route = "explore_screen/$query",
    )

    object BookmarkScreen : Routes("bookmark_screen")

    object ProfileScreen : Routes("profile_screen")

    object ArticleDetailScreen : Routes("article_detail_screen")

    object SubscribeTopicScreen : Routes("select_topics_screen")

    data class ListScreen(
        val topic: String = "{slime_topic}",
        val topicId: String = "{slime_topic_id}"
    ) : Routes(
        route = "list_screen/$topic/$topicId",
    )

    companion object {
        const val articleDetailDeepLink = "https://slime-kt.herokuapp.com/article_detail_screen="
        fun articleDetailLink(id: Int): Uri = (articleDetailDeepLink + "$id").toUri()
    }
}
