/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.plugins

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(json = DefaultJson)
    }
}

val DefaultJson: Json = Json {
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    allowStructuredMapKeys = true
    prettyPrint = false
    useArrayPolymorphism = true
    ignoreUnknownKeys = true
}
