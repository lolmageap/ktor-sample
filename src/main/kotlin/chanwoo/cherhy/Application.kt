package chanwoo.cherhy

import chanwoo.cherhy.plugins.configureHTTP
import chanwoo.cherhy.plugins.configureRouting
import chanwoo.cherhy.plugins.configureSecurity
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureRouting()
}
