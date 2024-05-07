package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

typealias ChatId = Long

object Chats : BaseLongIdTable("chat", "id") {
    val content = varchar("content", 5000)
    val sender: Column<EntityID<CustomerId>> = reference("customer_id", Customers)
}

class Chat(id: EntityID<ChatId>) : BaseEntity(id, Chats) {
    companion object : BaseEntityClass<Chat>(Chats)
    var content by Chats.content
    var sender by Chats.sender
}