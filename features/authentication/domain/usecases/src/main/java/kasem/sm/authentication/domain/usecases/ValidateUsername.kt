/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.usecases

import android.app.Application
import kasem.sm.authentication.domain.usecases.model.ValidatedResult
import kasem.sm.authentication.domain.usecases.model.containsSpecialCharacters
import kasem.sm.authentication.domain.usecases.model.containsWhiteSpaces
import kasem.sm.authentication.domain.usecases.model.onlyWithLettersAndDigits

class ValidateUsername(
    private val application: Application
) {
    operator fun invoke(updatedUsername: String): ValidatedResult {
        val formattedValue = updatedUsername.onlyWithLettersAndDigits.take(10)

        if (updatedUsername.containsWhiteSpaces) {
            return ValidatedResult(
                formattedValue = formattedValue,
                message = application.getString(R.string.space_not_allowed)
            )
        }

        if (updatedUsername.containsSpecialCharacters && updatedUsername.isNotEmpty()) {
            return ValidatedResult(
                formattedValue = formattedValue,
                message = application.getString(R.string.special_chars_not_allowed)
            )
        }

        if (updatedUsername.trim().length !in (4..10)) {
            return ValidatedResult(formattedValue = formattedValue, message = application.getString(R.string.username_len_should_bw))
        }

        return ValidatedResult(formattedValue)
    }
}
