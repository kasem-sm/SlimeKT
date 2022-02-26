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

fun SharedPreferences.observe(key: String, defValue: String?): Flow<String?> {
    val flow = MutableStateFlow(getString(key, defValue))

    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        flow.value = getString(key, defValue)
    }

    registerOnSharedPreferenceChangeListener(listener)

    return flow
        .onCompletion { unregisterOnSharedPreferenceChangeListener(listener) }
}
