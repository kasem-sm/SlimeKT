/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.utils

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import slime.com.data.response.SlimeResponse

fun PipelineContext<Unit, ApplicationCall>.get(key: String): String? {
    return call.parameters[key]
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.respondWith(message: SlimeResponse<T>) {
    call.respond(HttpStatusCode.OK, message)
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.respondWith(data: T) {
    call.respond(HttpStatusCode.OK, SlimeResponse(success = true, data = data))
}

@JvmName("respondWithNullable")
suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.respondWith(data: T?) {
    call.respond(HttpStatusCode.OK, SlimeResponse(success = true, data = data))
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.respondWithBadRequest() {
    call.respond(HttpStatusCode.BadRequest, SlimeResponse<Unit>(false, "Where's my body?"))
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.respondWithResult(doWork: () -> ServiceResult) {
    when (val result = doWork()) {
        is ServiceResult.Success -> respondWith<Unit>(SlimeResponse(true, result.message))
        is ServiceResult.Error -> respondWith<Unit>(SlimeResponse(false, result.message))
    }
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.respondWithResult(data: T? = null, doWork: () -> ServiceResult) {
    when (val result = doWork()) {
        is ServiceResult.Success -> respondWith(SlimeResponse(true, result.message, data))
        is ServiceResult.Error -> respondWith(SlimeResponse(false, result.message, data))
    }
}
