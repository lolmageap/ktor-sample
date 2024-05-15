package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.video.VideoService
import chanwoo.cherhy.ktor.util.extension.customerId
import chanwoo.cherhy.ktor.util.extension.getVideo
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.GET_VIDEO
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.GET_VIDEOS
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.UPLOAD_VIDEO
import chanwoo.cherhy.ktor.util.extension.videoId
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.video() {
    val videoService: VideoService by inject()

    authenticate(AUTHORITY) {
        get(GET_VIDEOS) {
            val customer = call.jwt.customerId
        }
    }

    authenticate(AUTHORITY) {
        get(GET_VIDEO) {
            val customer = call.jwt.customerId
            val videoId = call.videoId
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