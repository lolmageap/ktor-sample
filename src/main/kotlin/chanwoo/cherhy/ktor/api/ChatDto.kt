package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.chat.Chat
import chanwoo.cherhy.ktor.domain.chat.ChatId
import chanwoo.cherhy.ktor.domain.chat.ChatRoom
import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.customer.CustomerId

data class CreateChatRoomRequest(
    val targetCustomerIds: List<CustomerId>,
    val name: String,
    val description: String,
    val password: String?,
    val maxUser: Int,
)

data class ChatRoomResponse(
    val id: ChatRoomId,
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
                id = ChatRoomId.of(id.value),
                name = name,
                maxUser = maxUser,
                currentUsers = currentUser,
                totalChats = totalChats,
                owner = CustomerId.of(owner.value),
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
                id = ChatId.of(id.value),
                chatRoomId = ChatRoomId.of(chatRoom.value),
                senderId = CustomerId.of(sender.value),
                message = content,
            )
        }
    }
}