package chanwoo.cherhy.ktor.usecase

import chanwoo.cherhy.ktor.api.CreateChatRoomRequest
import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.chat.ChatRoomLinkService
import chanwoo.cherhy.ktor.domain.chat.ChatRoomService
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.util.extension.encode
import java.util.*

class CreateChatRoomUseCase(
    private val customerService: CustomerService,
    private val passwordEncoder: Base64.Encoder,
    private val chatRoomService: ChatRoomService,
    private val chatRoomLinkService: ChatRoomLinkService,
) {
    fun execute(
        request: CreateChatRoomRequest,
        ownerId: CustomerId,
    ): ChatRoomId {
        val encodedPassword = request.password?.let { passwordEncoder.encode(it) }

        val owner = customerService.get(ownerId)
        val targetCustomer = customerService.getAll(request.targetCustomerIds)
        val targetCustomerIds = targetCustomer.map { it.id }
        val allCustomerIds = targetCustomerIds + ownerId

        require(allCustomerIds.size <= request.maxUser) { "room is full" }

        val chatRoom = chatRoomService.create(
            roomName = request.name,
            encodedPassword = encodedPassword,
            description = request.description,
            maxUsers = request.maxUser,
            currentUsers = allCustomerIds.size,
            ownerId = owner.id,
        )

        chatRoomLinkService.create(
            chatRoomId = chatRoom.id,
            customerIds = allCustomerIds,
        )

        return chatRoom.id
    }
}