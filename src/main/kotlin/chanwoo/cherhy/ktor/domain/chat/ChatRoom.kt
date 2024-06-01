package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID

object ChatRooms : BaseLongIdTable("chat_room", "chat_room_id") {
    val name = varchar("name", 255)
    var password = varchar("password", 255).nullable()
    val maxUser = integer("max_user").default(0)
    val currentUser = integer("current_user").default(0)
    val totalChats = integer("total_chats").default(0)
    val owner = reference("customer_id", Customers)
}

class ChatRoom(
    id: EntityID<ChatRoomId>,
) : BaseEntity(
    id = id.unwrap(),
    table = ChatRooms,
) {
    companion object : BaseEntityClass<ChatRoom>(ChatRooms)
    var name by ChatRooms.name
    var password by ChatRooms.password
    var maxUser by ChatRooms.maxUser
    var currentUser by ChatRooms.currentUser
    var totalChats by ChatRooms.totalChats
    var owner by ChatRooms.owner
}

@JvmInline
value class ChatRoomId(
    val value: Long,
): Comparable<ChatRoomId> {
    override fun compareTo(
        other: ChatRoomId,
    ) = value.compareTo(other.value)
    companion object {
        fun of(
            value: Long,
        ) = ChatRoomId(value)
    }
}

private fun EntityID<ChatRoomId>.unwrap() = EntityID(value.value, ChatRooms)