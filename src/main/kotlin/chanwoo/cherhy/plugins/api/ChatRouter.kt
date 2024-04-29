package chanwoo.cherhy.plugins.api

import chanwoo.cherhy.plugins.domain.chat.Connection
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.Collections

private val connectionFactory = Collections.synchronizedSet(HashSet<Connection>())

fun Route.chat() {
    webSocket("/echo") {
        connectionFactory + Connection(this)
        send("Please enter your name")

        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                val message = "${connectionFactory.find { it.session == this }?.name}: $receivedText"
                connectionFactory.forEach { it.session.send(message) }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        } finally {
            connectionFactory - Connection(this)
        }
    }
}