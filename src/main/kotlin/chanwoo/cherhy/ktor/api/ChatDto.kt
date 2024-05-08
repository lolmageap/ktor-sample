package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.Chat
import chanwoo.cherhy.ktor.domain.chat.ChatId
import chanwoo.cherhy.ktor.domain.chat.ChatRoom
import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.customer.CustomerId

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
    val owner: CustomerId,
) {
    companion object {
        fun of(
            chatRoom: ChatRoom,
        ) = with(chatRoom) {
            ChatRoomResponse(
                id = id.value,
                name = name,
                maxUser = maxUser,
                currentUsers = currentUser,
                totalChats = totalChats,
                owner = owner.value,
            )
        }
    }
}

data class ChatResponse(
    val id: ChatId,
    val chatRoomId: ChatRoomId,
    val senderId: CustomerId,
    val message: String,
) {
    companion object {
        fun of(
            chat: Chat,
        ) = with(chat) {
            ChatResponse(
                id = id.value,
                chatRoomId = chatRoom.value,
                senderId = sender.value,
                message = content,
            )
        }
    }
}