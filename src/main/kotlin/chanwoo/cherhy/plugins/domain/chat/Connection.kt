package chanwoo.cherhy.plugins.domain.chat

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

data class Connection(
    val session: DefaultWebSocketSession,
) {
    companion object {
        var lastId = AtomicInteger(0)
    }
    val name = "user${lastId.getAndIncrement()}"
}