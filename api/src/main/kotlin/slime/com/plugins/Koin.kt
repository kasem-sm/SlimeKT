/*
 * Copyright (C) 2022, Kasem S.M
 * All rights reserved.
 */
package slime.com.plugins

import io.ktor.application.Application
import io.ktor.application.install
import slime.com.di.mainModule

fun Application.configureKoin() {
    install(org.koin.ktor.ext.Koin) {
        modules(mainModule)
    }
}
