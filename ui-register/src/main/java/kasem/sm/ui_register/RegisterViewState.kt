/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_register

data class RegisterViewState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordVisibility: Boolean = false,
    val isAccountDiscoverable: Boolean = true
) {
    companion object {
        val EMPTY = RegisterViewState()
    }
}
