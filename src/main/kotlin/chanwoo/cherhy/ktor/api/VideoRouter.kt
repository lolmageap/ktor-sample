package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.config.MinioConfig
import chanwoo.cherhy.ktor.domain.video.StreamingService
import chanwoo.cherhy.ktor.util.extension.customerName
import chanwoo.cherhy.ktor.util.extension.getVideo
import chanwoo.cherhy.ktor.util.extension.jwt
import chanwoo.cherhy.ktor.util.property.EndPoint.VIDEO.GET_VIDEO
import chanwoo.cherhy.ktor.util.property.EndPoint.VIDEO.UPLOAD_VIDEO
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.video() {
    val minioClient = MinioConfig.init()
    val streamingService = StreamingService(minioClient)

    get(GET_VIDEO) {
        streamingService.getVideo()
    }

    post(UPLOAD_VIDEO) {
        val name = call.jwt.customerName
        val video = call.getVideo()
        streamingService.uploadVideo(name, video)
    }
}