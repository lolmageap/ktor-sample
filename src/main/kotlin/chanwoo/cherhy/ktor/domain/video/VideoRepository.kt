package chanwoo.cherhy.ktor.domain.video

import chanwoo.cherhy.ktor.api.UploadVideoRequest
import chanwoo.cherhy.ktor.api.VideoResponse
import chanwoo.cherhy.ktor.domain.customer.CustomerId
import chanwoo.cherhy.ktor.domain.customer.Customers
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.selectAll

interface VideoRepository {
    fun save(
        customer: CustomerId,
        request: UploadVideoRequest,
    ): VideoResponse

    fun findAll(): List<VideoResponse>

    fun findById(
        id: VideoId,
    ): VideoResponse
}

class VideoRepositoryImpl : VideoRepository {
    override fun save(
        customer: CustomerId,
        request: UploadVideoRequest,
    ) =
        Video.new {
            name = request.name
            uniqueName = request.uniqueName
            size = request.size
            owner = EntityID(customer, Customers)
        }.let(VideoResponse::of)

    override fun findAll() =
        Videos.selectAll()
            .map(Video::wrapRow)
            .map(VideoResponse::of)

    override fun findById(
        id: VideoId,
    ) =
        Video.findById(id)
            ?.let(VideoResponse::of)
            ?: throw IllegalArgumentException("해당 비디오가 존재하지 않습니다.")
}