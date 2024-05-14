package chanwoo.cherhy.ktor.config

import chanwoo.cherhy.ktor.util.ApplicationConfigUtils.getMinio
import chanwoo.cherhy.ktor.util.property.MinioProperty.ACCESS_KEY
import chanwoo.cherhy.ktor.util.property.MinioProperty.SECRET_KEY
import chanwoo.cherhy.ktor.util.property.MinioProperty.URL
import io.minio.MinioClient

object MinioConfig {
    private val minioUrl = getMinio(URL)
    private val minioAccessKey = getMinio(ACCESS_KEY)
    private val minioSecretKey = getMinio(SECRET_KEY)

    fun init(): MinioClient {
        return MinioClient.builder().endpoint(minioUrl)
            .credentials(minioAccessKey, minioSecretKey).build()
    }
}