package chanwoo.cherhy

import chanwoo.cherhy.plugins.config.configureHTTP
import chanwoo.cherhy.plugins.config.configureRouting
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureRouting()

    install(ContentNegotiation) {
        jackson()
    }
}