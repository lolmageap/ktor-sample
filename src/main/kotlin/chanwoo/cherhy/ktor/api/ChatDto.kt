package chanwoo.cherhy.ktor.api

data class CreateChatRoomRequest(
    val targetCustomerIds: List<Long>,
    val name: String,
    val description: String,
    val password: String?,
    val maxUser: Int,
)