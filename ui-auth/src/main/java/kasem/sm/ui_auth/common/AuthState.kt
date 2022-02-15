/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_auth.common

import androidx.compose.runtime.Immutable

@Immutable
data class AuthState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordVisibility: Boolean = false,
    val isAccountDiscoverable: Boolean = true
) {
    companion object {
        val EMPTY = AuthState()
    }
}
