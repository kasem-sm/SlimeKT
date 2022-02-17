/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
@file:OptIn(ExperimentalMaterialNavigationApi::class)
package kasem.sm.slime.navigation

import androidx.compose.material.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.slime.ui_home.HomeScreen
import kasem.sm.common_ui.util.Routes
import kasem.sm.ui_article_list.ListScreen
import kasem.sm.ui_auth.login.LoginScreen
import kasem.sm.ui_auth.register.RegisterScreen
import kasem.sm.ui_detail.DetailScreen
import kasem.sm.ui_explore.ExploreScreen
import kasem.sm.ui_profile.ProfileScreen
import kasem.sm.ui_subscribe_category.SubscribeCategoryScreen

internal fun NavGraphBuilder.attachRegistrationScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    bottomSheet(Routes.RegisterScreen.route) {
        RegisterScreen(
            viewModel = hiltViewModel(),
            onRegistrationSuccess = {
                navController.popBackStack()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

internal fun NavGraphBuilder.attachLoginScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    bottomSheet(route = Routes.LoginScreen.route) {
        LoginScreen(
            viewModel = hiltViewModel(),
            onLoginSuccess = {
                navController.popBackStack()
            },
            onSignUpClicked = {
                navController.popBackStack()
                navController.navigate(Routes.RegisterScreen.route)
            },
            snackbarHostState = snackbarHostState
        )
    }
}

internal fun NavGraphBuilder.attachHomeScreen(
    imageLoader: ImageLoader,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    composable(Routes.HomeScreen.route) {
        HomeScreen(
            viewModel = hiltViewModel(),
            snackbarHostState = snackbarHostState,
            imageLoader = imageLoader,
            onArticleClick = { id ->
                navController.navigate(Routes.articleDetailLink(id))
            },
            navigateTo = { route ->
                navController.navigate(route)
            }
        )
    }
}

internal fun NavGraphBuilder.attachExploreScreen(
    navController: NavController,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
) {
    composable(Routes.ExploreScreen().route) {
        ExploreScreen(
            viewModel = hiltViewModel(),
            imageLoader = imageLoader,
            snackbarHostState = snackbarHostState,
            onArticleClick = { id ->
                navController.navigate(Routes.articleDetailLink(id))
            },
            onCategoryClick = { title, id ->
                navController.navigate(Routes.ListScreen(title, id).route)
            }
        )
    }
}

internal fun NavGraphBuilder.attachProfileScreen(
    navController: NavController
) {
    composable(Routes.ProfileScreen.route) {
        ProfileScreen(
            viewModel = hiltViewModel(),
            onLogOutSuccess = {
                navController.popBackStack()
            },
            navigateTo = { route ->
                navController.navigate(route)
            }
        )
    }
}

internal fun NavGraphBuilder.attachArticleDetailScreen(
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
) {
    composable(
        route = Routes.ArticleDetailScreen.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = Routes.articleDetailDeepLink + "{id}"
            }
        )
    ) {
        DetailScreen(
            imageLoader = imageLoader,
            viewModel = hiltViewModel(),
            snackbarHostState = snackbarHostState
        )
    }
}

internal fun NavGraphBuilder.attachSelectTopicsScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    composable(Routes.SubscribeCategoryScreen.route) {
        SubscribeCategoryScreen(
            viewModel = hiltViewModel(),
            snackbarHostState = snackbarHostState,
            onSubscriptionSaved = {
                navController.popBackStack()
            },
            navigateTo = {
                navController.navigate(it)
            }
        )
    }
}

internal fun NavGraphBuilder.attachListScreen(
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    composable(Routes.ListScreen().route) {
        ListScreen(
            viewModel = hiltViewModel(),
            imageLoader = imageLoader,
            onArticleClick = { id ->
                navController.navigate(Routes.articleDetailLink(id))
            },
            snackbarHostState = snackbarHostState,
            navigateTo = {
                navController.navigate(it)
            }
        )
    }
}
