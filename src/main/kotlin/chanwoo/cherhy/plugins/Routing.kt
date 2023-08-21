package chanwoo.cherhy.plugins

import chanwoo.cherhy.plugins.CustomerRouter.Companion.customerRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        customerRouting()
    }

    routing {
        get("/") {
            call.respondText("찬우씨 하이")
        }
    }

}
