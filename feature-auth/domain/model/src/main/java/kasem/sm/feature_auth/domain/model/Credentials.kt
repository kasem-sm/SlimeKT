/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_auth.domain.model

data class Credentials(
    val username: String = "",
    val password: String = "",
    val isAccountDiscoverable: Boolean = true
)
