package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.api.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        home()
        swaggerUI("/swagger")
        customer()
        chat()
        chatRoom()
        video()
    }
}