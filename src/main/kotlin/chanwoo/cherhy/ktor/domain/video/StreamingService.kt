package chanwoo.cherhy.ktor.domain.video

import chanwoo.cherhy.ktor.util.ApplicationConfigUtils
import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getStreaming
import chanwoo.cherhy.ktor.util.property.MinioProperty
import chanwoo.cherhy.ktor.util.property.StreamingProperty.CHUNK_SIZE
import chanwoo.cherhy.ktor.util.property.StreamingProperty.OBJECT_PART_SIZE
import io.minio.MinioClient
import io.minio.PutObjectArgs
import java.io.ByteArrayInputStream

class StreamingService(
    private val minioClient: MinioClient,
) {
    fun getVideo() {
        TODO("아직 미구현~!")
    }

    fun uploadVideo(
        name: String,
        videoBytes: ByteArrayInputStream,
    ) {
        // TODO: name encoding 하자.
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(name)
                .stream(videoBytes, videoBytes.available().toLong(), objectPartSize)
                .build()
        )
    }

    companion object {
        private val chunkSize = getStreaming(CHUNK_SIZE)
        private val objectPartSize = getStreaming(OBJECT_PART_SIZE)
        private val bucket = ApplicationConfigUtils.getMinio(MinioProperty.BUCKET)
    }
}