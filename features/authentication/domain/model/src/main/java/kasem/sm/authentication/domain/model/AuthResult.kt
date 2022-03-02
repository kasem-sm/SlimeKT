/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.model

/**
 * A collection of possible results for an attempt to register the user
 */

sealed class AuthResult {
    object Success : AuthResult()

    data class Exception(val throwable: Throwable) : AuthResult()

    data class EmptyCredentials(
        val isUsernameEmpty: Boolean,
        val isPasswordEmpty: Boolean,
    ) : AuthResult()
}
