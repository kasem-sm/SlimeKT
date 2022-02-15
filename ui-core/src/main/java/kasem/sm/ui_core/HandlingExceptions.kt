/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

import androidx.annotation.StringRes

fun navigate(
    route: String
) = UiEvent.NavigateTo(route)

fun showMessage(
    value: String,
) = UiEvent.ShowMessage(UiText.StringText(value))

fun showMessage(
    @StringRes res: Int
) = UiEvent.ShowMessage(UiText.ResourceText(res))
