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

    val chatRoomId: ChatRoomId = call.parameters["chat-room-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("chat-room-id is required.")

    val videoId: VideoId = call.parameters["video-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("video-id is required.")

    val customerId: CustomerId = call.parameters["customer-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("customer-id is required.")

    val liveStreamId: LiveStreamId = call.parameters["live-stream-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("live-stream-id is required.")
}