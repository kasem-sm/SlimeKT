/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.usecases.model

val String.onlyWithLettersAndDigits get() = filter { it.isLetterOrDigit() }

val String.withoutWhiteSpaces get() = filter { !it.isWhitespace() }

val String.containsWhiteSpaces get() = any { it.isWhitespace() }

val String.containsSpecialCharacters get() = !(matches("[a-zA-Z0-9\\s]+".toRegex()))
