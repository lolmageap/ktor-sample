package chanwoo.cherhy.ktor.util.extension

import chanwoo.cherhy.ktor.api.UploadVideoRequest
import chanwoo.cherhy.ktor.domain.chat.ChatRoomId
import chanwoo.cherhy.ktor.domain.video.VideoId
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_RANGE
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.AccessDeniedException
import java.util.UUID

val mapper = jacksonObjectMapper()

val ApplicationCall.id: Long
    get() = this.parameters["id"]?.toLongOrNull()
        ?: throw IllegalArgumentException("Invalid entity id")

val ApplicationCall.jwt: JWTPrincipal
    get() = this.principal<JWTPrincipal>()
        ?: throw AccessDeniedException("Invalid token")

val ApplicationCall.chatRoomId: ChatRoomId
    get() = this.parameters["room-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("room-id is required.")

val ApplicationCall.videoId: VideoId
    get() = this.parameters["video-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("video-id is required.")

val ApplicationCall.lastVideoByte: Long
    get() = this.request.headers[CONTENT_RANGE]
        ?.substringAfter("bytes=")
        ?.substringBefore("-")
        ?.toLong()
        ?: 0

val ApplicationCall.customerId: Long
    get() = this.parameters["customer-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("customer-id is required.")

val ApplicationCall.liveStreamId: Long
    get() = this.parameters["live-stream-id"]
        ?.toLong()
        ?: throw IllegalArgumentException("live-stream-id is required.")

suspend fun ApplicationCall.getVideo(): UploadVideoRequest {
    val multipart = this.receiveMultipart()

    val videoName = multipart.readPart()
        ?.name
        ?: throw IllegalArgumentException("video is required.")

    val isVideo = videoName.endsWith(".mp4")
    require(isVideo) { "Invalid video format." }

    val byteArrayOutputStream = ByteArrayOutputStream()

    multipart.forEachPart { part ->
        if (part is PartData.FileItem) {
            part.streamProvider().use { inputStream ->
                inputStream.copyTo(byteArrayOutputStream)
            }
        }
        part.dispose()
    }

    val data = ByteArrayInputStream(
        byteArrayOutputStream.toByteArray()
    )

    return UploadVideoRequest.of(
        name = videoName,
        uniqueName = UUID.randomUUID().toString(),
        data = data,
    )
}

inline fun <reified T : Any> ApplicationCall.getQueryParams(): T {
    return this.request.queryParameters.toClass()
}

inline fun <reified T : Any> Parameters.toClass(): T {
    val map = entries().associate {
        it.key to (it.value.getOrNull(0)
            ?: throw IllegalArgumentException("Missing value for key ${it.key}"))
    }
    return mapper.convertValue(map, T::class.java)
}