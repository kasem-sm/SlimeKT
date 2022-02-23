/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun SubscribeTopicScreen(
    viewModel: SubscribeTopicVM,
    snackbarHostState: SnackbarHostState,
    onSubscriptionSaved: () -> Unit,
    navigateTo: (String) -> Unit,
) {
    val viewState by rememberFlow(viewModel.state)
        .collectAsState(SubscribeTopicState.EMPTY)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = onSubscriptionSaved,
        onRouteReceived = navigateTo
    )

    SubscribeTopicContent(
        state = viewState,
        onRefresh = viewModel::refresh,
        saveRecommendedValues = viewModel::saveUserSubscribedTopics,
        updateList = viewModel::updateList,
        showAuthenticationSheet = viewModel::checkAuthenticationStatus
    )
}
