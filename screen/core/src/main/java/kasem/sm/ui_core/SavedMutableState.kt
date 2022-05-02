/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.ui_core

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow

// TODO: add credits

/**
 * A class that act as a [MutableStateFlow] but
 * saves the value and retrieves it upon process death
 * with the help of [SavedStateHandle]
 */
class SavedMutableState<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defValue: T,
) {
    private val _state: MutableStateFlow<T> = MutableStateFlow(
        savedStateHandle.get<T>(key) ?: defValue
    )

    var value: T
        get() = _state.value
        set(value) {
            _state.value = value
            savedStateHandle[key] = value
        }

    val flow get() = _state
}
