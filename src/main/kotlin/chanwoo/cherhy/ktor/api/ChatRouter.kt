package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.ChatRoomLinkService
import chanwoo.cherhy.ktor.domain.chat.Connection
import chanwoo.cherhy.ktor.util.ChatRoomId
import chanwoo.cherhy.ktor.util.extension.chatRoomId
import chanwoo.cherhy.ktor.util.extension.customerId
import chanwoo.cherhy.ktor.util.extension.customerName
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.property.EndPoint.CHAT.ECHO
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
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
            val customerName = call.jwt.customerName
            val customerId = call.jwt.customerId
            val chatRoomId = call.chatRoomId

            chatRoomLinkService.ifAllowed(chatRoomId, customerId)
            val connection = connect(chatRoomId)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val message = "$customerName: $receivedText"

                    connectionFactoryMap[chatRoomId]
                        ?.forEach { it.session.send(message) }
                }
            } catch (e: Exception) {
                logger.error { e.localizedMessage }
            } finally {
                connectionFactoryMap[chatRoomId]
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