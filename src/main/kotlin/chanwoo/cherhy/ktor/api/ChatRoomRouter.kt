package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.usecase.CreateChatRoomUseCase
import chanwoo.cherhy.ktor.util.EndPoint.CHATROOM.CREATE_CHAT_ROOM
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.extension.customerId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.chatRoom() {
    val createChatRoomUseCase: CreateChatRoomUseCase by inject()

    authenticate(AUTHORITY) {
        post(CREATE_CHAT_ROOM) {
            val request = call.receive<CreateChatRoomRequest>()
            val ownerId = call.jwt.customerId

            val chatRoomId = createChatRoomUseCase.execute(request, ownerId)
            call.respond(HttpStatusCode.Created, chatRoomId)
        }
    }
}