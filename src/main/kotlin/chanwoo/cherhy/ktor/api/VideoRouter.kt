package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.video.VideoService
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.GET_VIDEO
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.GET_VIDEOS
import chanwoo.cherhy.ktor.util.EndPoint.VIDEO.UPLOAD_VIDEO
import chanwoo.cherhy.ktor.util.extension.*
import chanwoo.cherhy.ktor.util.property.SecurityProperty.AUTHORITY
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.video() {
    val videoService: VideoService by inject()

    authenticate(AUTHORITY) {
        get(GET_VIDEOS) {
            val customer = call.jwt.customerId
        }
    }

    // TODO : 현재 동영상을 전체 가져오고 있지만 chunk 단위로 가져오는 방법으로 수정해야함
    authenticate(AUTHORITY) {
        get(GET_VIDEO) {
            val customer = call.jwt.customerId
            val videoId = call.videoId
            val lastVideoByte = call.lastVideoByte
            val video = videoService.getVideo(customer, videoId, lastVideoByte)

//            call.response.header("Content-Type", "video/mp4")
//            call.response.header("Accept-Ranges", "bytes")
//            call.response.header(
//                "Content-Length",
//                calculateContentLengthHeader(parsedRange, video.size)
//            )
//            call.response.header(
//                "Content-Range",
//                constructContentRangeHeader(parsedRange, chunkWithMetadata.metadata.size)
//            )
//
//            call.respondBytes(
//                status = HttpStatusCode.PartialContent,
//                bytes = chunkWithMetadata.chunk,
//            )
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