package chanwoo.cherhy.ktor.domain.chat

import org.jetbrains.exposed.dao.id.EntityID

class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun create(
        roomName: String,
        encodedPassword: String?,
        description: String,
        maxUsers: Int,
        ownerId: EntityID<Long>,
    ) {
        chatRoomRepository.create(
            roomName = roomName,
            encodedPassword = encodedPassword,
            description = description,
            maxUsers = maxUsers,
            ownerId = ownerId,
        )
    }
}