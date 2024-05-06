package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.util.CustomerId
import io.ktor.websocket.*

data class Connection(
    val session: DefaultWebSocketSession,
    val customerId: CustomerId,
)