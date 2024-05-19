package chanwoo.cherhy.ktor.domain.video

import chanwoo.cherhy.ktor.api.UploadVideoRequest
import chanwoo.cherhy.ktor.api.VideoResponse
import chanwoo.cherhy.ktor.config.MinioConfig
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getStreaming
import chanwoo.cherhy.ktor.util.property.MinioProperty.BUCKET
import chanwoo.cherhy.ktor.util.property.StreamingProperty.CHUNK_SIZE
import chanwoo.cherhy.ktor.util.property.StreamingProperty.OBJECT_PART_SIZE
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs

class VideoService(
    private val videoRepository: VideoRepository,
) {
    private val minioClient = MinioConfig.init()

    fun getVideo(
        customerId: CustomerId,
        videoId: VideoId,
        lastVideoByte: Long,
    ): ByteArray {
        val videoInformation = videoRepository.findById(videoId)
        require(videoInformation.owner == customerId) { "해당 비디오에 접근할 권한이 없습니다." }
        return minioClient.fetchVideo(videoInformation.uniqueName, lastVideoByte).readAllBytes()
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

    fun getVideos(
        customer: CustomerId,
    ): List<VideoResponse> {
        return videoRepository.findAll(customer)
    }

    private fun MinioClient.fetchVideo(
        uniqueName: String,
        lastVideoByte: Long,
    ) =
        minioClient.getObject(
            GetObjectArgs
                .builder()
                .bucket(bucket)
                .offset(lastVideoByte)
                .length(chunkSize)
                .`object`(uniqueName)
                .build()
        )

    companion object {
        private val chunkSize = getStreaming(CHUNK_SIZE)
        private val objectPartSize = getStreaming(OBJECT_PART_SIZE)
        private val bucket = ApplicationConfigUtils.getMinio(BUCKET)
    }
}