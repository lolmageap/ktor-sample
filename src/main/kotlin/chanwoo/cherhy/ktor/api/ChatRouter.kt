package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.Connection
import chanwoo.cherhy.ktor.util.EndPoint.CHAT.ECHO
import chanwoo.cherhy.ktor.util.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.jwt
import chanwoo.cherhy.ktor.util.room
import chanwoo.cherhy.ktor.util.username
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import mu.KotlinLogging
import java.util.*

typealias Room = String

private val connectionFactoryMap = Collections.synchronizedMap(
    HashMap<Room, MutableList<Connection>>()
)
private val logger = KotlinLogging.logger { }

fun Route.chat() {
    authenticate(AUTHORITY) {
        webSocket(ECHO) {
            val username = call.jwt.username
            val room = call.room

            val connection = Connection(this)
            connectionFactoryMap.getOrPut(
                key = room,
                defaultValue = { mutableListOf() }
            ).add(connection)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val message = "$username: $receivedText"

                    connectionFactoryMap[room]
                        ?.forEach { it.session.send(message) }
                }
            } catch (e: Exception) {
                logger.error { e.localizedMessage }
            } finally {
                connectionFactoryMap[room]
                    ?.remove(connection)
            }
        }
    }
}