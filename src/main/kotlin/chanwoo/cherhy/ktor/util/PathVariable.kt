package chanwoo.cherhy.ktor.util

import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.livestream.LiveStreamId
import chanwoo.cherhy.ktor.domain.video.VideoId
import io.ktor.server.application.*

class PathVariable(call: ApplicationCall) {
    val id = call.parameters["id"]
        ?.toLongOrNull()
        ?: throw IllegalArgumentException("id is required.")

    val chatRoomId = call.parameters["chat-room-id"]
        ?.toLong()?.let(ChatRoomId::of)
        ?: throw IllegalArgumentException("chat-room-id is required.")

    val videoId = call.parameters["video-id"]
        ?.toLong()?.let(VideoId::of)
        ?: throw IllegalArgumentException("video-id is required.")

    val customerId = call.parameters["customer-id"]
        ?.toLong()?.let(CustomerId::of)
        ?: throw IllegalArgumentException("customer-id is required.")

    val liveStreamId = call.parameters["live-stream-id"]
        ?.toLong()?.let(LiveStreamId::of)
        ?: throw IllegalArgumentException("live-stream-id is required.")
}