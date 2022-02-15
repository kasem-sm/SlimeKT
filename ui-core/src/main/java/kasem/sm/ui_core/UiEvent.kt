/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kasem.sm.ui_core.UiText.Companion.get
import kotlinx.coroutines.flow.SharedFlow

sealed class UiEvent {
    data class ShowMessage(val uiText: UiText) : UiEvent()
    data class NavigateTo(val route: String) : UiEvent()
    data class SendData(val data: Any) : UiEvent()
    object Success : UiEvent()
}

@Composable
fun SharedFlow<UiEvent>.safeCollector(
    onRouteReceived: suspend (route: String) -> Unit = {},
    onDataReceived: suspend (data: Any) -> Unit = {},
    onMessageReceived: suspend (message: String) -> Unit = {},
    onSuccessCallback: () -> Unit = {}
) = apply {
    val context = LocalContext.current
    LaunchedEffect(key1 = this) {
        collect {
            when (it) {
                is UiEvent.ShowMessage -> onMessageReceived(it.uiText.get(context))
                is UiEvent.NavigateTo -> onRouteReceived(it.route)
                is UiEvent.SendData -> onDataReceived(it.data)
                is UiEvent.Success -> onSuccessCallback()
            }
        }
    }
}
