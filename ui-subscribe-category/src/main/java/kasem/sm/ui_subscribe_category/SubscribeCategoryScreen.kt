/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun SubscribeCategoryScreen(
    viewModel: SubscribeCategoryViewModel,
    snackbarHostState: SnackbarHostState,
    onSubscriptionSaved: () -> Unit,
    navigateTo: (String) -> Unit,
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(SubscribeCategoryState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = onSubscriptionSaved,
        onRouteReceived = navigateTo
    )

    SubscribeCategoryContent(
        state = viewState,
        onRefresh = viewModel::refresh,
        saveRecommendedValues = viewModel::saveUserSubscribedCategories,
        updateList = viewModel::updateList,
        showAuthenticationSheet = viewModel::checkAuthenticationStatus
    )
}
