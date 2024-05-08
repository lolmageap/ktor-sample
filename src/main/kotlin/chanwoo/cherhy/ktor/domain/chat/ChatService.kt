package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.util.model.PageRequest

class ChatService(
    private val chatRepository: ChatRepository,
) {
    fun save(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
        message: String,
    ) =
        chatRepository.save(chatRoomId, customerId, message)

    fun getAll(
        chatRoomId: ChatRoomId,
        pageRequest: PageRequest,
    ) =
        chatRepository.findAll(chatRoomId, pageRequest)
}