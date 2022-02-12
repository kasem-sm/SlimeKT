package slime.com.utils

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import slime.com.data.response.AuthResponse
import slime.com.data.response.SlimeResponse
import slime.com.plugins.userId
import slime.com.service.UserService

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

suspend inline fun PipelineContext<Unit, ApplicationCall>.getUserId(userService: UserService, doWork: (String) -> Unit) {
    val userId = call.userId ?: return
    val currentUser = userService.run { userId.getUserById() }
    currentUser?.let { user ->
        doWork(user.id)
    } ?: kotlin.run {
        respondWith(SlimeResponse<AuthResponse>(false, "You don't exists", null))
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.respondWithResult(doWork: () -> ServiceResult) {
    when (val result = doWork()) {
        is ServiceResult.Success -> respondWith<Unit>(SlimeResponse(true, result.message))
        is ServiceResult.Error -> respondWith<Unit>(SlimeResponse(false, result.message))
    }
}
