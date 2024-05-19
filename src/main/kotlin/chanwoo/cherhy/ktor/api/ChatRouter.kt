package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.chat.ChatRoomLinkService
import chanwoo.cherhy.ktor.domain.chat.ChatService
import chanwoo.cherhy.ktor.domain.chat.Connection
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.util.EndPoint.CHAT.ECHO
import chanwoo.cherhy.ktor.util.EndPoint.CHAT.GET_CHAT_HISTORY
import chanwoo.cherhy.ktor.util.extension.chatRoomId
import chanwoo.cherhy.ktor.util.extension.customerId
import chanwoo.cherhy.ktor.util.extension.customerName
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.model.PageRequest
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.util.*
import java.util.concurrent.ConcurrentHashMap

private val connectionFactoryMap =
    Collections.synchronizedMap(
        ConcurrentHashMap<ChatRoomId, MutableList<Connection>>()
    )
private val logger = KotlinLogging.logger { }

fun Route.chat() {
    val chatRoomLinkService: ChatRoomLinkService by inject()
    val chatService: ChatService by inject()

    authenticate(AUTHORITY) {
        webSocket(ECHO) {
            val customerName = call.jwt.customerName
            val customerId = call.jwt.customerId
            val chatRoomId = call.chatRoomId

            chatRoomLinkService.ifAllowed(chatRoomId, customerId)
            val connection = createConnection(chatRoomId, customerId)

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val message = "$customerName: $receivedText"

                    connectionFactoryMap[chatRoomId]
                        ?.forEach {
                            it.session.send(message)
                            chatService.save(chatRoomId, customerId, receivedText)
                        }
                }
            } catch (e: Exception) {
                logger.error { e.localizedMessage }
            } finally {
                connectionFactoryMap[chatRoomId]
                    ?.remove(connection)
            }
        }
    }

    // TODO: 회원이 속한 채팅방 인지 확인
    authenticate(AUTHORITY) {
        get(GET_CHAT_HISTORY) {
            val customerId = call.jwt.customerId
            val chatRoomId = call.chatRoomId
            val request = call.receive<PageRequest>()

            val chatResponses = chatService.getAll(chatRoomId, request)
            call.respond(HttpStatusCode.OK, chatResponses)
        }
    }
}

private fun DefaultWebSocketServerSession.createConnection(
    chatRoomId: ChatRoomId,
    customerId: CustomerId,
): Connection {
    val newConnection = Connection(this, customerId)
    connectionFactoryMap.getOrPut(chatRoomId) { mutableListOf() }.add(newConnection)
    return newConnection
}