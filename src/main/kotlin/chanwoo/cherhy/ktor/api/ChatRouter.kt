package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.Connection
import chanwoo.cherhy.ktor.util.EndPoint.CHAT.ECHO
import chanwoo.cherhy.ktor.util.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.username
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import mu.KotlinLogging
import java.util.*

private val connectionFactory = Collections.synchronizedSet(HashSet<Connection>())
private val logger = KotlinLogging.logger { }

fun Route.chat() {
    authenticate(AUTHORITY) {
        webSocket(ECHO) {
            val username = call.username

            connectionFactory += Connection(this)
            send("Please enter your name")

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val message = "$username: $receivedText"
                    connectionFactory.forEach { it.session.send(message) }
                }
            } catch (e: Exception) {
                logger.error { e.localizedMessage }
            } finally {
                connectionFactory -= Connection(this)
            }
        }
    }
}