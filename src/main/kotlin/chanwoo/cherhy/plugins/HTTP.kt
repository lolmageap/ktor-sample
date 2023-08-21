package chanwoo.cherhy.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureHTTP() {

    routing {
        swaggerUI(path = "김찬우")
    }

}
