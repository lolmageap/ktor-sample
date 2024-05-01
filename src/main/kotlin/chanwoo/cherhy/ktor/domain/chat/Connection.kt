package chanwoo.cherhy.ktor.domain.chat

import io.ktor.websocket.*

data class Connection(
    val session: DefaultWebSocketSession,
)