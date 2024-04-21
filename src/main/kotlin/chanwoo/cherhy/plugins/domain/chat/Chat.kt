package chanwoo.cherhy.plugins.domain.chat

import chanwoo.cherhy.plugins.domain.customer.Customers
import chanwoo.cherhy.plugins.util.BaseEntity
import chanwoo.cherhy.plugins.util.BaseEntityClass
import chanwoo.cherhy.plugins.util.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID

object Chats : BaseLongIdTable("chat", "id") {
    val content = varchar("content", 5000)
    val sender = reference("customer_id", Customers)
}

class Chat(id: EntityID<Long>) : BaseEntity(id, Chats) {
    companion object : BaseEntityClass<Chat>(Chats)
    var content by Chats.content
    var sender by Chats.sender
}