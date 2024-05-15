package chanwoo.cherhy.ktor.domain.video

import chanwoo.cherhy.ktor.api.UploadVideoRequest
import chanwoo.cherhy.ktor.config.MinioConfig
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getStreaming
import chanwoo.cherhy.ktor.util.property.MinioProperty
import chanwoo.cherhy.ktor.util.property.StreamingProperty.CHUNK_SIZE
import chanwoo.cherhy.ktor.util.property.StreamingProperty.OBJECT_PART_SIZE
import io.minio.PutObjectArgs

class VideoService(
    private val videoRepository: VideoRepository,
) {
    private val minioClient = MinioConfig.init()

    fun getVideo() {
        TODO("아직 미구현~!")
    }

    fun uploadVideo(
        customer: CustomerId,
        video: UploadVideoRequest,
    ) {
        videoRepository.save(customer, video)
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(video.uniqueName)
                .stream(video.data, video.size, objectPartSize)
                .build()
        )
    }

    companion object {
        private val chunkSize = getStreaming(CHUNK_SIZE)
        private val objectPartSize = getStreaming(OBJECT_PART_SIZE)
        private val bucket = ApplicationConfigUtils.getMinio(MinioProperty.BUCKET)
    }
}