package chanwoo.cherhy.ktor.api

data class CreateChatRoomRequest(
    val targetCustomerId: Long,
    val name: String,
    val description: String,
    val password: String?,
    val maxUser: Int,
)