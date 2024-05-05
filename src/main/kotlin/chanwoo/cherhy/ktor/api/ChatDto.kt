package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.ChatRoom

data class CreateChatRoomRequest(
    val targetCustomerIds: List<Long>,
    val name: String,
    val description: String,
    val password: String?,
    val maxUser: Int,
)

data class ChatRoomResponse(
    val id: Long,
    val name: String,
    val maxUser: Int,
    val currentUsers: Int,
    val totalChats: Int,
    val ownerId: Long,
) {
    companion object {
        fun of(chatRoom: ChatRoom): ChatRoomResponse {
            return ChatRoomResponse(
                id = chatRoom.id.value,
                name = chatRoom.name,
                maxUser = chatRoom.maxUser,
                currentUsers = chatRoom.currentUser,
                totalChats = chatRoom.totalChats,
                ownerId = chatRoom.owner.value,
            )
        }
    }
}