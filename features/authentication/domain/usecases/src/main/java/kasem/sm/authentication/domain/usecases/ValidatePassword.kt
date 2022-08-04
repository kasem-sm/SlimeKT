/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.usecases

import android.app.Application
import kasem.sm.authentication.domain.usecases.model.ValidatedResult
import kasem.sm.authentication.domain.usecases.model.containsWhiteSpaces
import kasem.sm.authentication.domain.usecases.model.withoutWhiteSpaces

class ValidatePassword(
    private val application: Application
) {
    operator fun invoke(updatedPassword: String): ValidatedResult {
        val formattedValue = updatedPassword.withoutWhiteSpaces.take(20)

        if (updatedPassword.containsWhiteSpaces) {
            return ValidatedResult(formattedValue, application.getString(R.string.space_not_allowed))
        }

        if (updatedPassword.trim().length !in (4..20)) {
            return ValidatedResult(formattedValue = formattedValue, message = application.getString(R.string.password_len_should_bw))
        }

        return ValidatedResult(formattedValue)
    }
}
