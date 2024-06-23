package chanwoo.cherhy.ktor.util

import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.livestream.LiveStreamId
import chanwoo.cherhy.ktor.domain.video.VideoId
import chanwoo.cherhy.ktor.util.Path.CHAT_ROOM_ID
import chanwoo.cherhy.ktor.util.Path.CUSTOMER_ID
import chanwoo.cherhy.ktor.util.Path.ID
import chanwoo.cherhy.ktor.util.Path.LIVE_STREAM_ID
import chanwoo.cherhy.ktor.util.Path.VIDEO_ID
import io.ktor.server.application.*

class PathVariable(
    call: ApplicationCall,
) {
    operator fun get(
        key: String,
    ) =
        path[key]
            ?: throw IllegalArgumentException("$key is required.")

    val id = call.parameters[ID]
        ?.toLongOrNull()
        ?: throw IllegalArgumentException("$ID is required.")

    val chatRoomId = call.parameters[CHAT_ROOM_ID]
        ?.toLong()?.let(ChatRoomId::of)
        ?: throw IllegalArgumentException("$CHAT_ROOM_ID is required.")

    val videoId = call.parameters[VIDEO_ID]
        ?.toLong()?.let(VideoId::of)
        ?: throw IllegalArgumentException("$VIDEO_ID is required.")

    val customerId = call.parameters[CUSTOMER_ID]
        ?.toLong()?.let(CustomerId::of)
        ?: throw IllegalArgumentException("$CUSTOMER_ID is required.")

    val liveStreamId = call.parameters[LIVE_STREAM_ID]
        ?.toLong()?.let(LiveStreamId::of)
        ?: throw IllegalArgumentException("$LIVE_STREAM_ID is required.")

    private val path = call.parameters
}

private object Path {
    const val ID = "id"
    const val CHAT_ROOM_ID = "chat-room-id"
    const val VIDEO_ID = "video-id"
    const val CUSTOMER_ID = "customer-id"
    const val LIVE_STREAM_ID = "live-stream-id"
}