/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.data.util

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion

/**
 * Inspired from: https://gist.github.com/ologe/eaa1dea1f94cdda1a39adcaf3886658a
 */

inline fun <reified T> SharedPreferences.observeKey(key: String, defValue: T): Flow<T> {
    val flow = MutableStateFlow(getItem(key, defValue))

    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        if (key == k) {
            flow.value = getItem(key, defValue)!!
        }
    }
    registerOnSharedPreferenceChangeListener(listener)

    return flow
        .onCompletion { unregisterOnSharedPreferenceChangeListener(listener) }
}

inline fun <reified T> SharedPreferences.getItem(key: String, default: T): T {
    return when (default) {
        is String -> getString(key, default) as T
        is Int -> getInt(key, default) as T
        is Boolean -> getBoolean(key, default) as T
        else -> throw IllegalArgumentException()
    }
}
