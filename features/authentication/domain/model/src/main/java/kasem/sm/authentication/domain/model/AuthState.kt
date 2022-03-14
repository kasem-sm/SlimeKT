/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.model

data class AuthState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordVisibility: Boolean = true,
    val isAccountDiscoverable: Boolean = true
) {
    companion object {
        val EMPTY = AuthState()
    }
}
