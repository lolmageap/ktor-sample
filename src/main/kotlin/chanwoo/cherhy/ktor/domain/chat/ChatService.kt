package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.CustomerId

class ChatService(
    private val chatRepository: ChatRepository,
) {
    fun save(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
        text: String,
    ) {
        chatRepository.save(chatRoomId, customerId, text)
    }
}