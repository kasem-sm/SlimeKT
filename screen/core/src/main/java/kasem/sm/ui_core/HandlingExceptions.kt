/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

import androidx.annotation.StringRes

fun navigate(
    navigationEvent: NavigationEvent
): UiEvent.NavigateTo = UiEvent.NavigateTo(navigationEvent)

fun showMessage(
    value: String,
): UiEvent.ShowMessage = UiEvent.ShowMessage(UiText.StringText(value))

fun showMessage(
    @StringRes res: Int
): UiEvent.ShowMessage = UiEvent.ShowMessage(UiText.ResourceText(res))

fun success(): UiEvent.Success = UiEvent.Success
