package chanwoo.cherhy.ktor.util

import io.ktor.server.application.*

class PathVariable(call: ApplicationCall) {
    val id = call.parameters["id"]?.toLongOrNull()
        ?: throw IllegalArgumentException("id is required.")

    val chatRoomId = call.parameters["chat-room-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("chat-room-id is required.")

    val videoId = call.parameters["video-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("video-id is required.")

    val customerId = call.parameters["customer-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("customer-id is required.")

    val liveStreamId = call.parameters["live-stream-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("live-stream-id is required.")
}