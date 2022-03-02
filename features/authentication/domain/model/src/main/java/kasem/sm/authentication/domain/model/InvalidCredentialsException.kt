/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.model

/**
 * A custom impl. of [Throwable] that occurs when a user tries to login
 * with Invalid credentials.
 */

class InvalidCredentialsException(override val message: String? = "Your username or password is incorrect") : Throwable(message)
class ServerException(override val message: String? = null) : Throwable(message)
