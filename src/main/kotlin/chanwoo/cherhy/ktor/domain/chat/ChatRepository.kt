package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.api.ChatResponse
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.PageRequest
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SortOrder

interface ChatRepository {
    fun save(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
        message: String,
    ): ChatResponse

    fun findAll(
        chatRoomId: ChatRoomId,
        pageRequest: PageRequest,
    ): List<ChatResponse>
}

class ChatRepositoryImpl : ChatRepository {
    override fun save(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
        message: String,
    ) =
        Chat.new {
            content = message
            sender = EntityID(customerId.value, Customers)
            chatRoom = EntityID(chatRoomId.value, ChatRooms)
        }.let(ChatResponse::of)

    override fun findAll(
        chatRoomId: ChatRoomId,
        pageRequest: PageRequest
    ) =
        Chat.find { Chats.chatRoom eq chatRoomId.value }
            .limit(pageRequest.size, pageRequest.offset)
            .orderBy(Chats.createdAt to SortOrder.DESC)
            .map(ChatResponse::of)
}