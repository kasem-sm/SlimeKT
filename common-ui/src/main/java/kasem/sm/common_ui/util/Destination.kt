/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import android.net.Uri
import androidx.core.net.toUri

sealed class Destination(
    val route: String,
) {
    object Main : Destination("main_route")

    object LoginScreen : Destination("login_screen")

    object RegisterScreen : Destination("sign_up_screen")

    object HomeScreen : Destination("home_screen")

    data class ExploreScreen(val query: String = "{slime_topic}") : Destination(
        route = "explore_screen/$query",
    )

    object BookmarkScreen : Destination("bookmark_screen")

    object ProfileScreen : Destination("profile_screen")

    object ArticleDetailScreen : Destination("article_detail_screen")

    object SubscribeTopicScreen : Destination("select_topics_screen")

    data class ListScreen(
        val topic: String = "{slime_topic}",
        val topicId: String = "{slime_topic_id}"
    ) : Destination(
        route = "list_screen/$topic/$topicId",
    )

    companion object {
        const val articleDetailDeepLink = "https://slime-kt.herokuapp.com/article_detail_screen="
        fun articleDetailLink(id: Int): Uri = (articleDetailDeepLink + "$id").toUri()
    }
}
