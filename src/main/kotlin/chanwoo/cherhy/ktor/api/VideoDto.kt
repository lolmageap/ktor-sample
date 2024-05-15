package chanwoo.cherhy.ktor.api

import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.video.Video
import chanwoo.cherhy.ktor.domain.video.VideoId
import java.io.ByteArrayInputStream

data class UploadVideoRequest(
    val name: String,
    val uniqueName: String,
    val data: ByteArrayInputStream,
) {
    val size: Long
        get() = data.available().toLong()
    companion object {
        fun of(
            name: String,
            uniqueName: String,
            data: ByteArrayInputStream,
        ) =
            UploadVideoRequest(
                name = name,
                uniqueName = uniqueName,
                data = data,
            )
    }
}

data class VideoResponse(
    val id: VideoId,
    val name: String,
    val uniqueName: String,
    val size: Long,
    val owner: CustomerId,
) {
    companion object {
        fun of(
            video: Video,
        ) =
            with(video) {
                VideoResponse(
                    id = id.value,
                    name = name,
                    uniqueName = uniqueName,
                    size = size,
                    owner = owner.value,
                )
            }
    }
}