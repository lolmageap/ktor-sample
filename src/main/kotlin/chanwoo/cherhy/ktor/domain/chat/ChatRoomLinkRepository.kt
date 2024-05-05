package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.CustomerId
import chanwoo.cherhy.ktor.util.ChatRoomId
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

interface ChatRoomLinkRepository {
    fun save(
        chatRoomId: ChatRoomId,
        customerIds: List<CustomerId>,
    )

    fun existsByChatRoomIdAndCustomerId(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
    ): Boolean
}

class ChatRoomLinkRepositoryImpl : ChatRoomLinkRepository {
    override fun save(
        chatRoomId: ChatRoomId,
        customerIds: List<CustomerId>,
    ) {
        val chatRooms = EntityID(chatRoomId, ChatRooms)

        customerIds.map {
            val customers = EntityID(it, Customers)
            ChatRoomLink.new {
                chatRoom = chatRooms
                customer = customers
            }
        }
    }

    override fun existsByChatRoomIdAndCustomerId(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
    ): Boolean {
        val chatRooms = EntityID(chatRoomId, ChatRooms)
        val customers = EntityID(customerId, Customers)

        return ChatRoomLink.find {
            (ChatRoomLinks.chatRoom eq chatRooms) and
                    (ChatRoomLinks.customer eq customers)
        }.count() > 0
    }
}