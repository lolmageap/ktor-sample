package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.util.CustomerId
import chanwoo.cherhy.ktor.util.ChatRoomId

class ChatRoomLinkService(
    private val chatRoomLinkRepository: ChatRoomLinkRepository,
) {
    fun create(
        chatRoomId: ChatRoomId,
        customerIds: List<CustomerId>,
    ) {
        chatRoomLinkRepository.save(
            chatRoomId = chatRoomId,
            customerIds = customerIds,
        )
    }

    fun ifAllowed(
        chatRoomId: ChatRoomId,
        customerId: CustomerId,
        block: () -> Exception = { IllegalArgumentException("Not allowed") },
    ) =
        chatRoomLinkRepository.existsByChatRoomIdAndCustomerId(
            chatRoomId = chatRoomId,
            customerId = customerId,
        ).let { isExists ->
            if (isExists) Unit
            else throw block()
        }
}