package chanwoo.cherhy.plugins.config

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("찬우씨 하이")
        }
    }
}