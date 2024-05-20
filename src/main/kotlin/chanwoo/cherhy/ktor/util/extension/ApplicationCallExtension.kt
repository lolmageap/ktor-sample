package chanwoo.cherhy.ktor.util.extension

import chanwoo.cherhy.ktor.api.UploadVideoRequest
import chanwoo.cherhy.ktor.util.PathVariable
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
import java.util.*

val mapper = jacksonObjectMapper()

val ApplicationCall.pathVariable
    get() = PathVariable(this)

val ApplicationCall.jwt
    get() = this.principal<JWTPrincipal>()
        ?: throw AccessDeniedException("Invalid token")

val ApplicationCall.lastVideoByte
    get() = this.request.headers[CONTENT_RANGE]
        ?.substringAfter("bytes=")
        ?.substringBefore("-")
        ?.toLong()
        ?: 0

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
    val map = this.entries().associate {
        it.key to (it.value.getOrNull(0)
            ?: throw IllegalArgumentException("Missing value for key ${it.key}"))
    }
    return mapper.convertValue(map, T::class.java)
}