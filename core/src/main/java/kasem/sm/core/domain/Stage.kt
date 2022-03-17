/*
 * Copyright (C) 2021, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.domain

import kasem.sm.core.utils.toMessage
import kotlinx.coroutines.flow.Flow

sealed class Stage {
    object Initial : Stage()
    object Success : Stage()
    data class Exception(val throwable: Throwable = UnknownError()) : Stage()

    companion object {
        val Stage.exception get() = (this as Exception).throwable
    }
}

/**
 * [collect] is an extension function which takes in
 * a loader which helps in showing the progressBar for each execution
 * and onError lambda gets triggered if any exception is thrown.
 *
 * Please refer to our documentation for more [details](https://kasem-sm.github.io/SlimeKT/guide/interactors/)
 */
suspend fun Flow<Stage>.collect(
    loader: ObservableLoader,
    onError: suspend (String) -> Unit,
    onSuccess: suspend () -> Unit = { },
) = collect { stage ->
    when (stage) {
        is Stage.Initial -> loader.start()
        is Stage.Success -> {
            onSuccess.invoke()
            loader.stop()
        }
        is Stage.Exception -> {
            onError.invoke(stage.throwable.toMessage)
            loader.stop()
        }
    }
}
