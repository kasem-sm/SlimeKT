/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
@file:OptIn(DelicateCoroutinesApi::class)

package kasem.sm.topic.subscription_manager_worker.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> LiveData<T>.asFlow(): Flow<T> = flow {
    val channel = Channel<T>(Channel.CONFLATED)
    val observer = Observer<T> {
        channel.trySend(it).isSuccess
    }
    withContext(Dispatchers.Main.immediate) {
        observeForever(observer)
    }
    try {
        for (value in channel) {
            emit(value)
        }
    } finally {
        GlobalScope.launch(Dispatchers.Main.immediate) {
            removeObserver(observer)
        }
    }
}
