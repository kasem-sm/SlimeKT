/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_subscribe_category

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kasem.sm.ui_core.rememberFlow
import kasem.sm.ui_core.safeCollector

@Composable
fun SubscribeCategoryScreen(
    viewModel: SubscribeCategoryViewModel,
    navigateAfterSelection: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val listOfCategories =
        rememberFlow(flow = viewModel.listOfCategories).collectAsState(initial = emptyList()).value

    val isLoading =
        rememberFlow(flow = viewModel.loadingStatus.flow).collectAsState(initial = false).value

    viewModel.uiEvent.safeCollector(
        onMessageReceived = snackbarHostState::showSnackbar,
        onRouteReceived = navigateAfterSelection
    )

    SubscribeCategoryContent(
        listOfCategories = listOfCategories,
        onRefresh = viewModel::refresh,
        saveRecommendedValues = viewModel::saveUserSubscribedCategories,
        isLoading = isLoading,
        updateList = viewModel::updateList
    )
}
