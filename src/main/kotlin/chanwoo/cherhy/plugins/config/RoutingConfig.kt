package chanwoo.cherhy.plugins.config

import chanwoo.cherhy.plugins.api.chat
import chanwoo.cherhy.plugins.api.customer
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") { call.respondText("health check") }
        swaggerUI("/swagger")
        customer()
        chat()
    }
}