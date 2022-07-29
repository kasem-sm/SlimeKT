package kasem.sm.slime.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.DestinationEnterTransition
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import kasem.sm.ui_article_list.ListVM
import kasem.sm.ui_article_list.destinations.ListScreenDestination
import kasem.sm.ui_auth.destinations.LoginScreenDestination
import kasem.sm.ui_auth.destinations.RegisterScreenDestination
import kasem.sm.ui_auth.login.LoginVM
import kasem.sm.ui_auth.register.RegisterVM
import kasem.sm.ui_bookmarks.BookmarksVM
import kasem.sm.ui_bookmarks.destinations.BookmarksScreenDestination
import kasem.sm.ui_detail.DetailVM
import kasem.sm.ui_detail.destinations.DetailScreenDestination
import kasem.sm.ui_explore.ExploreVM
import kasem.sm.ui_explore.destinations.ExploreScreenDestination
import kasem.sm.ui_home.HomeVM
import kasem.sm.ui_home.destinations.HomeScreenDestination
import kasem.sm.ui_profile.ProfileVM
import kasem.sm.ui_profile.destinations.ProfileScreenDestination
import kasem.sm.ui_subscribe_topic.SubscribeTopicVM
import kasem.sm.ui_subscribe_topic.destinations.SubscribeTopicScreenDestination

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
) {
    DestinationsNavHost(
        engine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() })
        ),
        navController = navController,
        navGraph = NavGraphs.root,
        modifier = modifier,
        dependenciesContainerBuilder = {
            dependency(CoreNavigator(navController))

            dependency(imageLoader)

            dependency(snackbarHostState)

            dependency(HomeScreenDestination) {
                hiltViewModel<HomeVM>()
            }

            dependency(LoginScreenDestination) {
                hiltViewModel<LoginVM>()
            }

            dependency(RegisterScreenDestination) {
                hiltViewModel<RegisterVM>()
            }

            dependency(BookmarksScreenDestination) {
                hiltViewModel<BookmarksVM>()
            }

            dependency(ListScreenDestination) {
                hiltViewModel<ListVM>()
            }

            dependency(SubscribeTopicScreenDestination) {
                hiltViewModel<SubscribeTopicVM>()
            }

            dependency(DetailScreenDestination) {
                hiltViewModel<DetailVM>()
            }

            dependency(ProfileScreenDestination) {
                hiltViewModel<ProfileVM>()
            }

            dependency(ExploreScreenDestination) {
                hiltViewModel<ExploreVM>()
            }
        }
    )
}