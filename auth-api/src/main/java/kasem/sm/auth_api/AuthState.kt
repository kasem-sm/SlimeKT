/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.auth_api

enum class AuthState {
    LOGGED_IN, LOGGED_OUT
}

sealed class UserData
object ID : UserData()
object Token : UserData()
