package kasem.sm.slime.ui.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import kasem.sm.ui_article_list.destinations.ListScreenDestination
import kasem.sm.ui_auth.destinations.LoginScreenDestination
import kasem.sm.ui_auth.destinations.RegisterScreenDestination
import kasem.sm.ui_bookmarks.destinations.BookmarksScreenDestination
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_detail.destinations.DetailScreenDestination
import kasem.sm.ui_explore.destinations.ExploreScreenDestination
import kasem.sm.ui_profile.destinations.ProfileScreenDestination
import kasem.sm.ui_subscribe_topic.destinations.SubscribeTopicScreenDestination

class CoreNavigator(private val navController: NavController) : CommonNavigator {
    override fun navigateEvent(event: NavigationEvent) {
        when(event) {
            is NavigationEvent.Detail -> {
                navController.navigate(DetailScreenDestination(event.id))
            }
            is NavigationEvent.ListScreen -> {
                navController.navigate(ListScreenDestination(topicId = event.title, topicQuery = event.id))
            }
            NavigationEvent.Login -> {
                navController.navigate(LoginScreenDestination)
            }
            NavigationEvent.Register -> {
                navController.navigate(RegisterScreenDestination)
            }
            NavigationEvent.Subscription -> {
                navController.navigate(SubscribeTopicScreenDestination)
            }
            NavigationEvent.Bookmarks -> {
                navController.navigate(BookmarksScreenDestination)
            }
            NavigationEvent.Explore -> {
                navController.navigate(ExploreScreenDestination)
            }
            NavigationEvent.Profile -> {
                navController.navigate(ProfileScreenDestination)
            }
            NavigationEvent.NavigateUp -> {
                navController.popBackStack()
            }
        }
    }
}