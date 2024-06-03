package chanwoo.cherhy.ktor.domain.video

import chanwoo.cherhy.ktor.domain.customer.Customers
import chanwoo.cherhy.ktor.util.model.BaseEntity
import chanwoo.cherhy.ktor.util.model.BaseEntityClass
import chanwoo.cherhy.ktor.util.model.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

object Videos: BaseLongIdTable("video", "id") {
    val name: Column<String> = varchar("name", 255)
    val uniqueName: Column<String> = varchar("unique_name", 255)
    val size: Column<Long> = long("size")
    val owner = reference("customer_id", Customers)
}

class Video(id: EntityID<VideoId>): BaseEntity(
    id = id.unwrap(),
    table = Videos,
) {
    var name by Videos.name
    var uniqueName by Videos.uniqueName
    var size by Videos.size
    var owner by Videos.owner
    companion object: BaseEntityClass<Video>(Videos)
}

@JvmInline
value class VideoId(
    val value: Long,
): Comparable<VideoId> {
    override fun compareTo(
        other: VideoId,
    ) = value.compareTo(other.value)
    companion object {
        @JvmStatic
        fun of(
            value: Long,
        ) = VideoId(value)
    }
}

private fun EntityID<VideoId>.unwrap() = EntityID(value.value, Videos)