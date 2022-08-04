/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.usecases.model

data class ValidatedResult(
    val formattedValue: String,
    val message: String? = null
)
