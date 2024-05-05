package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.util.CustomerId
import chanwoo.cherhy.ktor.util.ChatRoomId

class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun create(
        roomName: String,
        encodedPassword: String?,
        description: String,
        maxUsers: Int,
        currentUsers: Int,
        ownerId: CustomerId,
    ) =
        chatRoomRepository.save(
            roomName = roomName,
            encodedPassword = encodedPassword,
            description = description,
            maxUsers = maxUsers,
            currentUsers = currentUsers,
            ownerId = ownerId,
        )

    fun get(chatRoomId: ChatRoomId) =
        chatRoomRepository.findById(chatRoomId)
}