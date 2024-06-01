package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID

object Chats : BaseLongIdTable("chat", "id") {
    val content = varchar("content", 5000)
    val sender = reference("customer_id", Customers)
    val chatRoom = reference("chat_room_id", ChatRooms)
}

class Chat(
    id: EntityID<ChatId>,
) : BaseEntity(
    id = id.unwrap(),
    table = Chats,
) {
    companion object : BaseEntityClass<Chat>(Chats)
    var content by Chats.content
    var sender by Chats.sender
    var chatRoom by Chats.chatRoom
}

@JvmInline
value class ChatId(
    val value: Long,
): Comparable<ChatId> {
    override fun compareTo(
        other: ChatId,
    ) = value.compareTo(other.value)
    companion object {
        fun of(
            value: Long,
        ) = ChatId(value)
    }
}

private fun EntityID<ChatId>.unwrap() = EntityID(value.value, Chats)