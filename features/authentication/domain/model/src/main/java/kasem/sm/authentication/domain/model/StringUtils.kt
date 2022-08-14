/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.authentication.domain.model

val String.containsSpecialCharacters get() = !(matches("[a-zA-Z0-9\\s]+".toRegex()))

val String.containsWhiteSpaces get() = contains("\\s".toRegex())

val String.containsOnlyNumbers get() = (matches("[0-9]+".toRegex()))
