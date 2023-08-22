package chanwoo.cherhy

import chanwoo.cherhy.plugins.configureHTTP
import chanwoo.cherhy.plugins.configureRouting
import chanwoo.cherhy.plugins.configureSecurity
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
    configureSecurity()
    configureHTTP()
    configureRouting()

    install(ContentNegotiation) {
        jackson()
    }

}
