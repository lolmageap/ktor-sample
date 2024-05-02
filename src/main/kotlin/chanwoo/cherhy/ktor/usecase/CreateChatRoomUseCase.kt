package chanwoo.cherhy.ktor.usecase

import chanwoo.cherhy.ktor.api.CreateChatRoomRequest
import chanwoo.cherhy.ktor.domain.chat.ChatRoomService
import chanwoo.cherhy.ktor.domain.customer.CustomerService
import chanwoo.cherhy.ktor.util.encode
import java.util.*

class CreateChatRoomUseCase(
    private val customerService: CustomerService,
    private val passwordEncoder: Base64.Encoder,
    private val chatRoomService: ChatRoomService,
) {
    fun execute(
        request: CreateChatRoomRequest,
        ownerId: Long,
    ) {
        val encodedPassword = request.password?.let { passwordEncoder.encode(it) }

        val targetCustomer = customerService.get(request.targetCustomerId)
        val owner = customerService.get(ownerId)

        chatRoomService.create(
            roomName = request.name,
            encodedPassword = encodedPassword,
            description = request.description,
            maxUsers = request.maxUser,
            ownerId = owner.id,
        )

//        chatRoomInfoService.create(
//            chatRoomId,
//            listOf(owner.id, targetCustomer.id),
//        )
    }
}
