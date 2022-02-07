package slime.com

import io.ktor.application.Application
import slime.com.plugins.configureKoin
import slime.com.plugins.configureMonitoring
import slime.com.plugins.configureRouting
import slime.com.plugins.configureSecurity
import slime.com.plugins.configureSerialization
import slime.com.plugins.configureSockets

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureSecurity()
    configureRouting()
    configureSockets()
    configureSerialization()
    configureMonitoring()
}
