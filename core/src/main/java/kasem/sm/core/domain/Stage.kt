/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kasem.sm.core.domain.ObservableLoader.Companion.Loader
import kasem.sm.core.utils.toMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

sealed class Stage {
    object Success : Stage()
    data class Exception(val throwable: Throwable = UnknownError()) : Stage()
}

/**
 * [collect] is an extension function which takes in
 * a loader which helps in showing the progressBar for each execution
 * and onError lambda gets triggered if any exception is thrown.
 */
suspend fun Flow<Stage>.collect(
    loader: ObservableLoader,
    onError: suspend (String) -> Unit,
    onSuccess: suspend () -> Unit = { },
) = onStart { loader(Loader.START) }
    .onCompletion { loader(Loader.STOP) }
    .collectLatest { stage ->
        when (stage) {
            is Stage.Success -> onSuccess.invoke()
            is Stage.Exception -> onError.invoke(stage.throwable.toMessage)
        }
        loader(Loader.STOP)
    }
