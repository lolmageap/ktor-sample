package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.video.VideoService
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.GET_VIDEO
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.GET_VIDEOS
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.UPLOAD_VIDEO
import chanwoo.cherhy.ktor.util.extension.*
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import io.ktor.http.*
import io.ktor.http.ContentType.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.netty.handler.codec.http.HttpHeaders.Names.*
import org.koin.ktor.ext.inject
import java.lang.Byte.BYTES

fun Route.video() {
    val videoService: VideoService by inject()

    authenticate(AUTHORITY) {
        get(GET_VIDEOS) {
            val customerId = call.jwt.customerId
            val videos = videoService.getVideos(customerId)
            call.respond(videos)
        }
    }

    authenticate(AUTHORITY) {
        get(GET_VIDEO) {
            val customerId = call.jwt.customerId
            val videoId = call.videoId
            val lastVideoByte = call.lastVideoByte
            val video = videoService.getVideo(customerId, videoId, lastVideoByte)

            call.response.header(ACCEPT_RANGES, BYTES)
            call.response.header(CONTENT_LENGTH, video.size)
            call.response.header(CONTENT_RANGE, video.size)
            call.respondBytes(Video.MP4, HttpStatusCode.PartialContent) { video }
        }
    }

    authenticate(AUTHORITY) {
        post(UPLOAD_VIDEO) {
            val customer = call.jwt.customerId
            val video = call.getVideo()
            videoService.uploadVideo(customer, video)
        }
    }
}