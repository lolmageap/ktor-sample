package chanwoo.cherhy.ktor.domain.chat

import org.jetbrains.exposed.dao.id.EntityID

interface ChatRoomRepository {
    fun create(
        roomName: String,
        encodedPassword: String?,
        description: String,
        maxUsers: Int,
        currentUsers: Int,
        ownerId: EntityID<Long>,
    )
}

class ChatRoomRepositoryImpl: ChatRoomRepository {
    override fun create(
        roomName: String,
        encodedPassword: String?,
        description: String,
        maxUsers: Int,
        currentUsers: Int,
        ownerId: EntityID<Long>,
    ) {
        ChatRoom.new {
            name = roomName
            password = encodedPassword
            maxUser = maxUsers
            currentUser = currentUsers
            totalChats = 0
            owner = ownerId
        }
    }
}