/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.feature_auth.domain.model

val String.containsSpecialCharacters get() = !(matches("[a-zA-Z0-9]+".toRegex()))

val String.containsOnlyNumbers get() = (matches("[0-9]+".toRegex()))
