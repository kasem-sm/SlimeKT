/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.common_ui.util

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object Main : Routes(
        route = "main_route"
    )

    object LoginScreen : Routes(
        route = "login_screen"
    )

    object RegisterScreen : Routes(
        route = "sign_up_screen",
    )

    object HomeScreen : Routes(
        route = "home_screen"
    )

    data class ExploreScreen(val query: String = "{slime_category}") : Routes(
        route = "explore_screen/$query",
        arguments = listOf(
            navArgument("slime_category") {
                type = NavType.StringType
            }
        )
    )

    object ProfileScreen : Routes(
        route = "profile_screen"
    )

    object ArticleDetailScreen : Routes(
        route = "article_detail_screen",
    )

    object SubscribeCategoryScreen : Routes(
        route = "select_topics_screen"
    )

    data class ListScreen(
        val category: String = "{slime_category}",
        val categoryId: String = "{slime_category_id}"
    ) : Routes(
        route = "list_screen/$category/$categoryId",
        arguments = listOf(
            navArgument("slime_category") {
                type = NavType.StringType
            },
            navArgument("slime_category_id") {
                type = NavType.StringType
            }
        )
    )

    companion object {
        const val articleDetailDeepLink = "https://slime-kt.herokuapp.com/article_detail_screen="
        fun articleDetailLink(id: Int): Uri = (articleDetailDeepLink + "$id").toUri()
    }
}
