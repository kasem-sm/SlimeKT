package kasem.sm.slime.ui.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import kasem.sm.ui_article_list.destinations.ListScreenDestination
import kasem.sm.ui_auth.destinations.LoginScreenDestination
import kasem.sm.ui_auth.destinations.RegisterScreenDestination
import kasem.sm.ui_bookmarks.destinations.BookmarksScreenDestination
import kasem.sm.ui_detail.destinations.DetailScreenDestination
import kasem.sm.ui_explore.destinations.ExploreScreenDestination
import kasem.sm.ui_home.destinations.HomeScreenDestination
import kasem.sm.ui_profile.destinations.ProfileScreenDestination
import kasem.sm.ui_subscribe_topic.destinations.SubscribeTopicScreenDestination

object NavGraphs {
    val root = object : NavGraphSpec {
        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            HomeScreenDestination,
            LoginScreenDestination,
            RegisterScreenDestination,
            ExploreScreenDestination,
            ProfileScreenDestination,
            DetailScreenDestination,
            SubscribeTopicScreenDestination,
            ListScreenDestination,
            BookmarksScreenDestination
        ).associateBy { it.route }
        override val route: String = "root"
        override val startRoute: Route = HomeScreenDestination
    }
}