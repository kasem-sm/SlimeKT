/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_login

data class LoginViewState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordVisibility: Boolean = true
) {
    companion object {
        val EMPTY = LoginViewState()
    }
}
