package chanwoo.cherhy

import chanwoo.cherhy.plugins.config.configureDependencyInjection
import chanwoo.cherhy.plugins.config.configureJackson
import chanwoo.cherhy.plugins.config.configureRouting
import chanwoo.cherhy.plugins.config.configureWebSocket
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureWebSocket()
    configureJackson()
    configureDependencyInjection()
    configureRouting()
}