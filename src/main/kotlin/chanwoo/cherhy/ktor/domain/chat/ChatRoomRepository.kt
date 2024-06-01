package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.api.ChatRoomResponse
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.Customers
import org.jetbrains.exposed.dao.id.EntityID

interface ChatRoomRepository {
    fun save(
        roomName: String,
        encodedPassword: String?,
        description: String,
        maxUsers: Int,
        currentUsers: Int,
        ownerId: CustomerId,
    ): ChatRoomResponse

    fun findById(
        chatRoomId: ChatRoomId,
    ): ChatRoomResponse
}

class ChatRoomRepositoryImpl : ChatRoomRepository {
    override fun save(
        roomName: String,
        encodedPassword: String?,
        description: String,
        maxUsers: Int,
        currentUsers: Int,
        ownerId: CustomerId,
    ) =
        ChatRoom.new {
            name = roomName
            password = encodedPassword
            maxUser = maxUsers
            currentUser = currentUsers
            totalChats = 0
            owner = EntityID(ownerId.value, Customers)
        }.let(ChatRoomResponse::of)

    override fun findById(
        chatRoomId: ChatRoomId,
    ) =
        ChatRoom.findById(chatRoomId.value)
            ?.let(ChatRoomResponse::of)
            ?: throw IllegalArgumentException("ChatRoom not found")
}