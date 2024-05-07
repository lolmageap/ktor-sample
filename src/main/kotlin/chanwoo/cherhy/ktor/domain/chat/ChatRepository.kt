package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.PageRequest
import org.jetbrains.exposed.sql.selectAll

interface ChatRepository {
    fun findAllContent(
        pageRequest: PageRequest,
    ): List<String>

    fun save(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
        text: String,
    ) {
        TODO("Not yet implemented")
    }
}

class ChatRepositoryImpl: ChatRepository {
    override fun findAllContent(
        pageRequest: PageRequest,
    ) =
        Chat.find { Chats.sender eq Customers.id }
            .limit(pageRequest.size, pageRequest.offset)
            .map { it.content }

    fun findWithCustomer(
        pageRequest: PageRequest,
    ) =
        Chats.innerJoin(Customers)
            .selectAll()
            .limit(pageRequest.size, pageRequest.offset)
            .map { it[Chats.content] }
            .toList()
}