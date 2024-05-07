package chanwoo.cherhy.ktor.domain.chat

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import io.ktor.websocket.*

data class Connection(
    val session: DefaultWebSocketSession,
    val customerId: CustomerId,
)