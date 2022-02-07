/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package kasem.sm.core.utils

val Throwable.toMessage get() = message ?: localizedMessage ?: "Something went wrong!"

/**
 * helper function that returns appropriate [Result]
 */
suspend inline fun <T> withResult(crossinline doWork: suspend () -> T): Result<T> {
    return try {
        val data = doWork.invoke()
        Result.success(data)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

/**
 * helper function that throws exception when something goes wrong,
 * it's the responsibility of the class which calls the function
 * to handle the thrown exception
 */
@Throws(Exception::class)
suspend inline fun <X> slimeSuspendTry(crossinline doWork: suspend () -> X): X {
    return try {
        doWork.invoke()
    } catch (exception: Exception) {
        exception.printStackTrace()
        throw exception
    }
}

/**
 * helper function that throws exception when something goes wrong,
 * it's the responsibility of the class which calls the function
 * to handle the thrown exception
 */
@Throws(Exception::class)
inline fun <X> slimeTry(crossinline doWork: () -> X): X {
    return try {
        doWork.invoke()
    } catch (exception: Exception) {
        exception.printStackTrace()
        throw exception
    }
}
