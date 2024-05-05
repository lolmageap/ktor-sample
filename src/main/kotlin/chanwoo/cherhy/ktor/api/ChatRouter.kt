package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.ChatRoomLinkService
import chanwoo.cherhy.ktor.domain.chat.Connection
import chanwoo.cherhy.ktor.util.*
import chanwoo.cherhy.ktor.util.EndPoint.CHAT.ECHO
import chanwoo.cherhy.ktor.util.SecurityProperty.AUTHORITY
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.util.*

private val connectionFactoryMap =
    Collections.synchronizedMap(
        HashMap<ChatRoomId, MutableList<Connection>>()
    )
private val logger = KotlinLogging.logger { }

fun Route.chat() {
    val chatRoomLinkService: ChatRoomLinkService by inject()

    authenticate(AUTHORITY) {
        webSocket(ECHO) {
            val username = call.jwt.username
            val customerId = call.jwt.customerId
            val roomId = call.chatRoomId

            chatRoomLinkService.ifAllowed(roomId, customerId)
            val connection = connect(roomId)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val message = "$username: $receivedText"

                    connectionFactoryMap[roomId]
                        ?.forEach { it.session.send(message) }
                }
            } catch (e: Exception) {
                logger.error { e.localizedMessage }
            } finally {
                connectionFactoryMap[roomId]
                    ?.remove(connection)
            }
        }
    }
}

private fun DefaultWebSocketServerSession.connect(chatRoomId: ChatRoomId) =
    Connection(this).also { connection ->
        connectionFactoryMap.getOrPut(
            key = chatRoomId,
            defaultValue = { mutableListOf() }
        ).add(connection)
    }