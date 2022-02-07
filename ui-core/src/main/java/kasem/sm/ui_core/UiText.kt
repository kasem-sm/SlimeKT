/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {
    data class StringText(val value: String) : UiText()

    data class ResourceText(@StringRes val value: Int) : UiText()

    companion object {
        /**
         * Evaluates the value of [UiText] based on it's type.
         * @param[context] is necessary to evaluate a string resource.
         */

        fun UiText.get(context: Context): String {
            return when (this) {
                is StringText -> this.value
                is ResourceText -> context.getString(this.value)
            }
        }

        /**
         * Mini Helper function that allows us to get strings
         * from a [Composable] function
         * easily!
         */

        @Composable
        fun UiText.get(): String {
            return when (this) {
                is StringText -> this.value
                is ResourceText -> LocalContext.current.getString(this.value)
            }
        }
    }
}
