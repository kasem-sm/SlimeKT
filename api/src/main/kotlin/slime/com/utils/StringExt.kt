/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.utils

fun String.containsSpecialCharacters() = !(matches("[a-zA-Z0-9]+".toRegex()))

fun String.containsOnlyNumbers() = (matches("[0-9]+".toRegex()))
