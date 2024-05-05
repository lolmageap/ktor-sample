package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.api.chat
import chanwoo.cherhy.ktor.api.customer
import chanwoo.cherhy.ktor.util.EndPoint.HOME
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get(HOME) { call.respondText("health check") }
        swaggerUI("/swagger")
        customer()
        chat()
    }
}