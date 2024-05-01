package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.BaseEntity
import chanwoo.cherhy.ktor.util.BaseEntityClass
import chanwoo.cherhy.ktor.util.BaseLongIdTable
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