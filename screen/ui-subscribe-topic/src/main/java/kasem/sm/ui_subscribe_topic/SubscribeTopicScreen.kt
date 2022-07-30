/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_topic

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import kasem.sm.ui_core.CommonNavigator
import kasem.sm.ui_core.NavigationEvent
import kasem.sm.ui_core.rememberStateWithLifecycle
import kasem.sm.ui_core.safeCollector

@Destination
@Composable
fun SubscribeTopicScreen(
    viewModel: SubscribeTopicVM,
    snackbarHostState: SnackbarHostState,
    navigator: CommonNavigator
) {
    val viewState by rememberStateWithLifecycle(viewModel.state)

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onSuccessCallback = {
            navigator.navigateEvent(NavigationEvent.NavigateUp)
        },
        onNavigate = navigator::navigateEvent
    )

    SubscribeTopicContent(
        state = viewState,
        onRefresh = viewModel::refresh,
        saveRecommendedValues = viewModel::saveUserSubscribedTopics,
        updateList = viewModel::updateList,
        showAuthenticationSheet = viewModel::checkAuthenticationStatus,
        navigateBack = { navigator.navigateEvent(NavigationEvent.NavigateUp) }
    )
}
